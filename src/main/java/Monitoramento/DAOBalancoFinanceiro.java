package Monitoramento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Aplicacao.App;
import Modelo.BalancoFinanceiro;

public class DAOBalancoFinanceiro {
    private static EntityManagerFactory factory = App.getEMF();
    private static EntityManager emBalancoFinanceiro;

    public DAOBalancoFinanceiro() {
    	emBalancoFinanceiro = factory.createEntityManager();
    }

    public List<BalancoFinanceiro> listaBalancosFinanceiros() {
        List<BalancoFinanceiro> balancos = null;
        try {
            TypedQuery<BalancoFinanceiro> query = emBalancoFinanceiro.createQuery("FROM BalancoFinanceiro", BalancoFinanceiro.class);
            balancos = query.getResultList();
        } finally {
            emBalancoFinanceiro.close();
        }
        return balancos;
    }

    public BalancoFinanceiro buscarBalancoFinanceiroPorId(final int id) {
        try {
            TypedQuery<BalancoFinanceiro> query = emBalancoFinanceiro.createQuery("FROM BalancoFinanceiro WHERE id = :id", BalancoFinanceiro.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            emBalancoFinanceiro.close();
        }
    }

    public void salvarOuAtualizarBalancoFinanceiro(BalancoFinanceiro balancoFinanceiro) {
        try {
            emBalancoFinanceiro.getTransaction().begin();

            // Verificar se já existe um balanço financeiro para o mês atual
            BalancoFinanceiro balancoExistente = buscarBalancoAtual();
            if (balancoExistente != null) {
                // Atualiza os valores do balanço existente
                balancoExistente.setMovimentoMensal(balancoFinanceiro.getMovimentoMensal());
                balancoExistente.setLucro(balancoFinanceiro.getLucro());
                balancoExistente.setDivida(balancoFinanceiro.getDivida());
                emBalancoFinanceiro.merge(balancoExistente);
            } else {
                // Caso não exista, salva um novo balanço financeiro
                emBalancoFinanceiro.persist(balancoFinanceiro);
            }

            emBalancoFinanceiro.getTransaction().commit();
        } catch (Exception e) {
            if (emBalancoFinanceiro.getTransaction().isActive()) {
                emBalancoFinanceiro.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (emBalancoFinanceiro != null && emBalancoFinanceiro.isOpen()) {
                emBalancoFinanceiro.close();
            }
        }
    }


    public void removerBalancoFinanceiro(BalancoFinanceiro balancoFinanceiro) {
        try {
            emBalancoFinanceiro.getTransaction().begin();
            BalancoFinanceiro bf = emBalancoFinanceiro.find(BalancoFinanceiro.class, balancoFinanceiro.getId());
            emBalancoFinanceiro.remove(bf);
            emBalancoFinanceiro.getTransaction().commit();
        } catch (Exception e) {
            if (emBalancoFinanceiro.getTransaction().isActive()) {
                emBalancoFinanceiro.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emBalancoFinanceiro.close();
        }
    }

    public void atualizarBalancoFinanceiro(BalancoFinanceiro balancoFinanceiro) {
        try {
            emBalancoFinanceiro.getTransaction().begin();
            emBalancoFinanceiro.merge(balancoFinanceiro);
            emBalancoFinanceiro.getTransaction().commit();
        } catch (Exception e) {
            if (emBalancoFinanceiro.getTransaction().isActive()) {
                emBalancoFinanceiro.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emBalancoFinanceiro.close();
        }
    }

    public List<BalancoFinanceiro> buscarBalancosPorData(LocalDate data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = data.format(formatter);
            
            TypedQuery<BalancoFinanceiro> query = emBalancoFinanceiro.createQuery(
                "FROM BalancoFinanceiro WHERE data = :data", BalancoFinanceiro.class);
            query.setParameter("data", formattedDate);
            
            return query.getResultList();
        } finally {
            emBalancoFinanceiro.close();
        }
    }
    
    public BalancoFinanceiro buscarBalancoAtual() {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        try {
            TypedQuery<BalancoFinanceiro> query = emBalancoFinanceiro.createQuery(
                    "FROM BalancoFinanceiro WHERE MONTH(dataHoraBalanco) = :mes AND YEAR(dataHoraBalanco) = :ano", BalancoFinanceiro.class);
            query.setParameter("mes", mesAtual);
            query.setParameter("ano", anoAtual);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static EntityManager getEmBalancoFinanceiro() {
        return emBalancoFinanceiro;
    }

    public static void fecharEmfBalancoFinanceiro() {
        if (emBalancoFinanceiro != null && emBalancoFinanceiro.isOpen()) {
            emBalancoFinanceiro.close();
            factory.close();
        }
    }
}
package Monitoramento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import Aplicacao.App;
import Modelo.BalancoGeral;
import Modelo.Funcionario;

public class DAOBalancoGeral {

    private static EntityManagerFactory factory = App.getEMF();
    private static EntityManager emBalancoGeral;

    public DAOBalancoGeral() {
        emBalancoGeral = factory.createEntityManager();
    }

    public BalancoGeral obterOuCriarBalancoGeralParaHoje(Funcionario FuncAtivo) {
        LocalDate hoje = LocalDate.now();

        try {
            TypedQuery<BalancoGeral> query = emBalancoGeral.createQuery(
                    "FROM BalancoGeral b WHERE b.dataHoraBalanco = :hoje", BalancoGeral.class);
            query.setParameter("hoje", hoje);

            List<BalancoGeral> balancos = query.getResultList();

            if (balancos.isEmpty()) {
                BalancoGeral novoBalanco = new BalancoGeral();
                novoBalanco.setDataHoraBalanco(LocalDateTime.now());
                novoBalanco.setUsuarioCaixa(FuncAtivo);
                ZoneId fusoHorarioBahia = ZoneId.of("America/Bahia");
                LocalDateTime agora = LocalDateTime.now(fusoHorarioBahia);
                novoBalanco.setDataHoraCaixaAberto(agora);
                salvarBalancoGeral(novoBalanco);
                return novoBalanco;
            } else {
                return balancos.get(0);
            }
        } finally {
            emBalancoGeral.close();
        }
    }

    public void salvarBalancoGeral(BalancoGeral balancoGeral) {
        try {
            emBalancoGeral.getTransaction().begin();
            emBalancoGeral.persist(balancoGeral);
            emBalancoGeral.getTransaction().commit();
        } catch (Exception e) {
            if (emBalancoGeral.getTransaction().isActive()) {
                emBalancoGeral.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emBalancoGeral.close();
        }
    }

    public void removerBalancoGeral(BalancoGeral balancoGeral) {
        try {
            emBalancoGeral.getTransaction().begin();
            BalancoGeral bg = emBalancoGeral.find(BalancoGeral.class, balancoGeral.getId());
            emBalancoGeral.remove(bg);
            emBalancoGeral.getTransaction().commit();
        } catch (Exception e) {
            if (emBalancoGeral.getTransaction().isActive()) {
                emBalancoGeral.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emBalancoGeral.close();
        }
    }

    public void atualizarBalancoGeral(BalancoGeral balancoGeral) {
        try {
            emBalancoGeral.getTransaction().begin();
            emBalancoGeral.merge(balancoGeral);
            emBalancoGeral.getTransaction().commit();
        } catch (Exception e) {
            if (emBalancoGeral.getTransaction().isActive()) {
                emBalancoGeral.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emBalancoGeral.close();
        }
    }

    public boolean existeBalancoGeralParaHoje() {
        LocalDate hoje = LocalDate.now();
        try {
            TypedQuery<Long> query = emBalancoGeral.createQuery(
                    "SELECT COUNT(b) FROM BalancoGeral b WHERE b.dataHoraBalanco = :hoje", Long.class);
            query.setParameter("hoje", hoje);
            return query.getSingleResult() > 0;
        } finally {
            emBalancoGeral.close();
        }
    }

    // Método para buscar balanços gerais do mês atual
    public List<BalancoGeral> buscarBalancosGeraisDoMesAtual() {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        try {
            TypedQuery<BalancoGeral> query = emBalancoGeral.createQuery(
                    "FROM BalancoGeral b WHERE MONTH(b.dataHoraBalanco) = :mes AND YEAR(b.dataHoraBalanco) = :ano",
                    BalancoGeral.class);
            query.setParameter("mes", mesAtual);
            query.setParameter("ano", anoAtual);
            return query.getResultList();
        } finally {
            emBalancoGeral.close();
        }
    }

    public static EntityManager getEmBalancoGeral() {
        return emBalancoGeral;
    }

    public static void fecharEmfBalancoGeral() {
        if (emBalancoGeral != null && emBalancoGeral.isOpen()) {
            emBalancoGeral.close();
            factory.close();
        }
    }
}

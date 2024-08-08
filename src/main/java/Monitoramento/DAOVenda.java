package Monitoramento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Aplicacao.App;
import Modelo.BalancoGeral;
import Modelo.Venda;

public class DAOVenda {
    private static EntityManagerFactory factory = App.getEMF();
    private static EntityManager emVenda;

    public DAOVenda() {
        emVenda = factory.createEntityManager();
    }

    public List<Venda> listaVendas() {
        List<Venda> vendas = new ArrayList<>();
        try {
            TypedQuery<Venda> query = emVenda.createQuery("FROM Venda", Venda.class);
            vendas = query.getResultList();
        } finally {
            emVenda.close();
        }
        return vendas;
    }

    public List<Venda> listarVendasPorBalancoGeral(BalancoGeral balancoGeral) {
        List<Venda> vendas = new ArrayList<>();
        try {
            TypedQuery<Venda> query = emVenda.createQuery(
                "FROM Venda v WHERE v.BalancoGeral_balancoGeralId = :BalancoGeral_balancoGeralId",
                Venda.class
            );
            query.setParameter("BalancoGeral_balancoGeralId", balancoGeral.getId());
            vendas = query.getResultList();
        } finally {
            emVenda.close();
        }
        return vendas;
    }

    public Venda buscarVendaPorId(final int id) {
        Venda venda = null;
        try {
            TypedQuery<Venda> query = emVenda.createQuery("FROM Venda WHERE id = :id", Venda.class);
            query.setParameter("id", id);
            venda = query.getSingleResult();
        } catch (NoResultException e) {
            // Pode retornar null se não encontrar resultado
        } finally {
            emVenda.close();
        }
        return venda;
    }

    public List<Venda> buscarVendasPorClienteCpf(final String cpfCliente) {
        List<Venda> vendas = new ArrayList<>();
        try {
            TypedQuery<Venda> query = emVenda.createQuery("FROM Venda WHERE cpfCliente = :cpfCliente", Venda.class);
            query.setParameter("cpfCliente", cpfCliente);
            vendas = query.getResultList();
        } finally {
            emVenda.close();
        }
        return vendas;
    }

    public void salvarVenda(Venda venda) {
        try {
            emVenda.getTransaction().begin();
            emVenda.persist(venda);
            emVenda.getTransaction().commit();
        } catch (Exception e) {
            if (emVenda.getTransaction().isActive()) {
                emVenda.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emVenda.close();
        }
    }

    public void removerVenda(Venda venda) {
        try {
            emVenda.getTransaction().begin();
            Venda vd = emVenda.find(Venda.class, venda.getId());
            if (vd != null) {
                emVenda.remove(vd);
            }
            emVenda.getTransaction().commit();
        } catch (Exception e) {
            if (emVenda.getTransaction().isActive()) {
                emVenda.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emVenda.close();
        }
    }

    public void atualizarVenda(Venda venda) {
        try {
            emVenda.getTransaction().begin();
            emVenda.merge(venda);
            emVenda.getTransaction().commit();
        } catch (Exception e) {
            if (emVenda.getTransaction().isActive()) {
                emVenda.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emVenda.close();
        }
    }

    public static EntityManager getEmVenda() {
        return emVenda;
    }

    public static void fecharEmfVenda() {
        if (emVenda != null && emVenda.isOpen()) {
            emVenda.close();
            factory.close();
        }
    }
}

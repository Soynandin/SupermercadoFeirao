package Monitoramento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Aplicacao.App;
import Modelo.Cliente;

public class DAOCliente {
    private static EntityManagerFactory factory = App.getEMF();
    private static EntityManager emCliente;

    public DAOCliente() {
        emCliente = factory.createEntityManager();
    }

    public List<Cliente> listaClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            TypedQuery<Cliente> query = emCliente.createQuery("FROM Cliente", Cliente.class);
            clientes = query.getResultList();
        } finally {
            emCliente.close();
        }
        return clientes;
    }

    public Cliente buscarCliente(final String cpf) {
        Cliente cliente = null;
        try {
            TypedQuery<Cliente> query = emCliente.createQuery("FROM Cliente WHERE cpf = :cpf", Cliente.class);
            query.setParameter("cpf", cpf);
            cliente = query.getSingleResult();
        } catch (NoResultException e) {
            // Pode retornar null se não encontrar resultado
        } finally {
            emCliente.close();
        }
        return cliente;
    }

    public void salvarCliente(Cliente cliente) {
        try {
            emCliente.getTransaction().begin();
            emCliente.persist(cliente);
            emCliente.getTransaction().commit();
        } catch (Exception e) {
            if (emCliente.getTransaction().isActive()) {
                emCliente.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emCliente.close();
        }
    }

    public void removerCliente(Cliente cliente) {
        try {
            emCliente.getTransaction().begin();
            Cliente cl = emCliente.find(Cliente.class, cliente.getId());
            if (cl != null) {
                emCliente.remove(cl);
            }
            emCliente.getTransaction().commit();
        } catch (Exception e) {
            if (emCliente.getTransaction().isActive()) {
                emCliente.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emCliente.close();
        }
    }

    public void atualizarCliente(Cliente cliente) {
        try {
            emCliente.getTransaction().begin();
            emCliente.merge(cliente);
            emCliente.getTransaction().commit();
        } catch (Exception e) {
            if (emCliente.getTransaction().isActive()) {
                emCliente.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emCliente.close();
        }
    }

    public static EntityManager getEmCliente() {
        return emCliente;
    }

    public static void fecharEmfCliente() {
        if (emCliente != null && emCliente.isOpen()) {
            emCliente.close();
            factory.close();
        }
    }
}

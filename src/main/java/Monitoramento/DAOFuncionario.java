package Monitoramento;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Aplicacao.App;
import Modelo.Funcionario;

public class DAOFuncionario {

    private static EntityManagerFactory factory = App.getEMF();
    private static EntityManager emFuncionario;

    public DAOFuncionario() {
        emFuncionario = factory.createEntityManager();
    }

    public List<Funcionario> ListaFuncionarios() {
        try {
            TypedQuery<Funcionario> query = emFuncionario.createQuery("FROM Funcionario", Funcionario.class);
            return query.getResultList();
        } finally {
            emFuncionario.close();
        }
    }

    public Funcionario BuscarFuncionario(final String cpf) {
        try {
            TypedQuery<Funcionario> query = emFuncionario.createQuery("FROM Funcionario WHERE cpf = :cpf", Funcionario.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            emFuncionario.close();
        }
    }

    public Funcionario BuscarUsuarioSenha(final String cpf, final String senha) {
        try {
            TypedQuery<Funcionario> query = emFuncionario.createQuery(
                    "FROM Funcionario WHERE cpf = :cpf AND senha = :senha", Funcionario.class);
            query.setParameter("cpf", cpf);
            query.setParameter("senha", senha);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            emFuncionario.close();
        }
    }

    public void SalvarFuncionario(Funcionario funcionario) {
        try {
            emFuncionario.getTransaction().begin();
            emFuncionario.persist(funcionario);
            emFuncionario.getTransaction().commit();
        } catch (Exception e) {
            if (emFuncionario.getTransaction().isActive()) {
                emFuncionario.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emFuncionario.close();
        }
    }

    public void RemoverFuncionario(Funcionario funcionario) {
        try {
            emFuncionario.getTransaction().begin();
            Funcionario fc = emFuncionario.find(Funcionario.class, funcionario.getId());
            emFuncionario.remove(fc);
            emFuncionario.getTransaction().commit();
        } catch (Exception e) {
            if (emFuncionario.getTransaction().isActive()) {
                emFuncionario.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emFuncionario.close();
        }
    }

    public void AtualizarFuncionario(Funcionario funcionario) {
        try {
            emFuncionario.getTransaction().begin();
            emFuncionario.merge(funcionario);
            emFuncionario.getTransaction().commit();
        } catch (Exception e) {
            if (emFuncionario.getTransaction().isActive()) {
                emFuncionario.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emFuncionario.close();
        }
    }

    public static EntityManager getEmFuncionario() {
        return emFuncionario;
    }

    public static void fecharEmfFuncionario() {
        if (emFuncionario != null && emFuncionario.isOpen()) {
            emFuncionario.close();
            factory.close();
        }
    }
}

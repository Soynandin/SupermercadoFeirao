package Monitoramento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Aplicacao.App;
import Modelo.Produto;

public class DAOProduto {
    private static EntityManagerFactory factory = App.getEMF();
    private static EntityManager emProduto;

    public DAOProduto() {
        emProduto = factory.createEntityManager();
    }

    public List<Produto> listaProdutos() {
        List<Produto> produtos = new ArrayList<>();
        try {
            TypedQuery<Produto> query = emProduto.createQuery("FROM Produto", Produto.class);
            produtos = query.getResultList();
        } finally {
            emProduto.close();
        }
        return produtos;
    }
    
    public List<Produto> listaProdutosPorCategoria(String categoria) {
        List<Produto> produtos = new ArrayList<>();
        try {
            TypedQuery<Produto> query = emProduto.createQuery("FROM Produto WHERE categoriaProduto = :categoria", Produto.class);
            query.setParameter("categoria", categoria);
            produtos = query.getResultList();
        } finally {
            emProduto.close();
        }
        return produtos;
    }
    
    public List<String> buscarCategorias() {
        List<String> categorias = new ArrayList<>();
        try {
            TypedQuery<String> query = emProduto.createQuery("SELECT DISTINCT p.categoriaProduto FROM Produto p", String.class);
            categorias = query.getResultList();
        } finally {
            emProduto.close();
        }
        return categorias;
    }

    public Produto buscarProdutoPorCodigo(final String codProduto) {
        Produto produto = null;
        try {
            TypedQuery<Produto> query = emProduto.createQuery("FROM Produto WHERE codProduto = :codProduto", Produto.class);
            query.setParameter("codProduto", codProduto);
            produto = query.getSingleResult();
        } catch (NoResultException e) {
        	return null;
        } finally {
            emProduto.close();
        }
        return produto;
    }

    public void salvarProduto(Produto produto) {
        try {
            emProduto.getTransaction().begin();
            emProduto.persist(produto);
            emProduto.getTransaction().commit();
        } catch (Exception e) {
            if (emProduto.getTransaction().isActive()) {
                emProduto.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emProduto.close();
        }
    }

    public void removerProduto(Produto produto) {
        try {
            emProduto.getTransaction().begin();
            Produto pr = emProduto.find(Produto.class, produto.getId());
            if (pr != null) {
                emProduto.remove(pr);
            }
            emProduto.getTransaction().commit();
        } catch (Exception e) {
            if (emProduto.getTransaction().isActive()) {
                emProduto.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emProduto.close();
        }
    }

    public void atualizarProduto(Produto produto) {
        try {
            emProduto.getTransaction().begin();
            emProduto.merge(produto);
            emProduto.getTransaction().commit();
        } catch (Exception e) {
            if (emProduto.getTransaction().isActive()) {
                emProduto.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            emProduto.close();
        }
    }

    public static EntityManager getEmProduto() {
        return emProduto;
    }

    public static void fecharEmfProduto() {
        if (emProduto != null && emProduto.isOpen()) {
            emProduto.close();
            factory.close();
        }
    }
}

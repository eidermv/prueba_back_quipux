package com.example.back.lista.application.query;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarLista<T> implements Query<List> {

    private static ListaRepo repo;

    private ListarLista(ListaRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<T> execute() {
        return repo.findAll();
    }

    public static final class ListarListaBuilder {
        private ListaRepo repo;

        private ListarListaBuilder() {
        }

        public static ListarListaBuilder aListarLista() {
            return new ListarListaBuilder();
        }

        public ListarListaBuilder withRepo(ListaRepo repo) {
            this.repo = repo;
            return this;
        }

        public ListarLista build() {
            return new ListarLista(repo);
        }
    }
}

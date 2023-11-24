package com.example.back.lista.application.query;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.query.Query;

import java.util.List;

public class ListarLista implements Query<List> {

    private ListaRepo repo;

    @Override
    public List execute() {
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
            ListarLista listarLista = new ListarLista();
            listarLista.repo = this.repo;
            return listarLista;
        }
    }
}

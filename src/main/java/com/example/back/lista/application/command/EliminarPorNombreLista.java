package com.example.back.lista.application.command;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.command.Command;

public class EliminarPorNombreLista implements Command<Boolean> {

    private ListaRepo repo;
    private String data;

    @Override
    public Boolean execute() {
        if (repo.existePorNombre(data)>0) {
            repo.eliminarPorNombre(data);
            return true;
        }
        return false;
    }

    public final class GuardarListaBuilder {
        private ListaRepo repo;
        private String data;

        private GuardarListaBuilder() {
        }

        public GuardarListaBuilder aGuardarLista() {
            return new GuardarListaBuilder();
        }

        public GuardarListaBuilder withRepo(ListaRepo repo) {
            this.repo = repo;
            return this;
        }

        public GuardarListaBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public EliminarPorNombreLista build() {
            EliminarPorNombreLista guardarLista = new EliminarPorNombreLista();
            guardarLista.data = this.data;
            guardarLista.repo = this.repo;
            return guardarLista;
        }
    }
}

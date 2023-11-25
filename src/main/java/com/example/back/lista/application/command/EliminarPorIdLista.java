package com.example.back.lista.application.command;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.command.Command;
import org.springframework.stereotype.Component;

@Component
public class EliminarPorIdLista implements Command<Boolean> {

    private ListaRepo repo;
    private int data;

    @Override
    public Boolean execute() {
        if (repo.existsById(data)) {
            repo.deleteById(data);
            return true;
        }
        return false;
    }


    public static final class EliminarPorIdListaBuilder {
        private ListaRepo repo;
        private int data;

        private EliminarPorIdListaBuilder() {
        }

        public static EliminarPorIdListaBuilder anEliminarPorIdLista() {
            return new EliminarPorIdListaBuilder();
        }

        public EliminarPorIdListaBuilder withRepo(ListaRepo repo) {
            this.repo = repo;
            return this;
        }

        public EliminarPorIdListaBuilder withData(int data) {
            this.data = data;
            return this;
        }

        public EliminarPorIdLista build() {
            EliminarPorIdLista eliminarPorIdLista = new EliminarPorIdLista();
            eliminarPorIdLista.data = this.data;
            eliminarPorIdLista.repo = this.repo;
            return eliminarPorIdLista;
        }
    }
}

package com.example.back.lista.application.command;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.command.Command;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EliminarPorNombreLista<T> implements Command<T> {

    private ListaRepo repo;
    private String data;

    private EliminarPorNombreLista() {
    }

    @Override
    public T execute() {
        T valor = (T) repo.buscarPorNombre(data);
        if (!Objects.isNull(valor)) {
            repo.eliminarPorNombre(data);
            return valor;
        }
        return null;
    }

    public static final class EliminarPorNombreListaBuilder {
        private ListaRepo repo;
        private String data;

        private EliminarPorNombreListaBuilder() {
        }

        public static EliminarPorNombreListaBuilder anEliminarPorNombreLista() {
            return new EliminarPorNombreListaBuilder();
        }

        public EliminarPorNombreListaBuilder withRepo(ListaRepo repo) {
            this.repo = repo;
            return this;
        }

        public EliminarPorNombreListaBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public EliminarPorNombreLista build() {
            EliminarPorNombreLista eliminarPorNombreLista = new EliminarPorNombreLista();
            eliminarPorNombreLista.data = this.data;
            eliminarPorNombreLista.repo = this.repo;
            return eliminarPorNombreLista;
        }
    }
}

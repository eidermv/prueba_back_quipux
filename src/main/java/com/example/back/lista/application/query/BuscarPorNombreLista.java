package com.example.back.lista.application.query;

import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.query.Query;
import org.springframework.stereotype.Component;

@Component
public class BuscarPorNombreLista<T> implements Query<T> {

    private ListaRepo repo;
    private String data;

    private BuscarPorNombreLista() {
    }

    @Override
    public T execute() {
        return (T) repo.buscarPorNombre(data);
    }


    public static final class BuscarPorNombreListaBuilder {
        private ListaRepo repo;
        private String data;

        private BuscarPorNombreListaBuilder() {
        }

        public static BuscarPorNombreListaBuilder aBuscarPorNombreLista() {
            return new BuscarPorNombreListaBuilder();
        }

        public BuscarPorNombreListaBuilder withRepo(ListaRepo repo) {
            this.repo = repo;
            return this;
        }

        public BuscarPorNombreListaBuilder withData(String data) {
            this.data = data;
            return this;
        }

        public BuscarPorNombreLista build() {
            BuscarPorNombreLista buscarPorNombreLista = new BuscarPorNombreLista();
            buscarPorNombreLista.data = this.data;
            buscarPorNombreLista.repo = this.repo;
            return buscarPorNombreLista;
        }
    }
}

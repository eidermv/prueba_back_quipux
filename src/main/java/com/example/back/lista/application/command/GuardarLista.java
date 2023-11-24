package com.example.back.lista.application.command;

import com.example.back.lista.domain.model.Lista;
import com.example.back.lista.domain.repository.ListaRepo;
import com.example.back.shared.application.command.Command;

public class GuardarLista<T> implements Command<T> {

    private ListaRepo repo;
    private T data;

    @Override
    public T execute() {
        return (T) repo.save(data);
    }

    public final class GuardarListaBuilder {
        private ListaRepo repo;
        private T data;

        private GuardarListaBuilder() {
        }

        public GuardarListaBuilder aGuardarLista() {
            return new GuardarListaBuilder();
        }

        public GuardarListaBuilder withRepo(ListaRepo repo) {
            this.repo = repo;
            return this;
        }

        public GuardarListaBuilder withData(T data) {
            this.data = data;
            return this;
        }

        public GuardarLista build() {
            GuardarLista guardarLista = new GuardarLista();
            guardarLista.data = this.data;
            guardarLista.repo = this.repo;
            return guardarLista;
        }
    }
}

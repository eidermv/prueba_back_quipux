package com.example.back.lista.application.command;

import com.example.back.lista.domain.repository.ListaCancionRepo;
import com.example.back.shared.application.command.Command;
import org.springframework.stereotype.Component;

@Component
public class GuardarListaCancion<T> implements Command<T> {

    private ListaCancionRepo repo;
    private T data;

    @Override
    public T execute() {
        return (T) repo.save(data);
    }

    private GuardarListaCancion() {
    }


    public static final class GuardarListaCancionBuilder<T> {
        private ListaCancionRepo repo;
        private T data;

        private GuardarListaCancionBuilder() {
        }

        public static GuardarListaCancionBuilder aGuardarListaCancion() {
            return new GuardarListaCancionBuilder();
        }

        public GuardarListaCancionBuilder withRepo(ListaCancionRepo repo) {
            this.repo = repo;
            return this;
        }

        public GuardarListaCancionBuilder withData(T data) {
            this.data = data;
            return this;
        }

        public GuardarListaCancion build() {
            GuardarListaCancion guardarListaCancion = new GuardarListaCancion();
            guardarListaCancion.repo = this.repo;
            guardarListaCancion.data = this.data;
            return guardarListaCancion;
        }
    }
}

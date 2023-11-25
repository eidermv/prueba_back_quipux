package com.example.back.lista.application.command;

import com.example.back.lista.domain.repository.CancionRepo;
import com.example.back.shared.application.command.Command;
import org.springframework.stereotype.Component;

@Component
public class GuardarCancion<T> implements Command<T> {

    private CancionRepo repo;
    private T data;

    @Override
    public T execute() {
        return (T) repo.save(data);
    }

    private GuardarCancion() {
    }


    public static final class GuardarCancionBuilder<T> {
        private CancionRepo repo;
        private T data;

        private GuardarCancionBuilder() {
        }

        public static GuardarCancionBuilder aGuardarCancion() {
            return new GuardarCancionBuilder();
        }

        public GuardarCancionBuilder withRepo(CancionRepo repo) {
            this.repo = repo;
            return this;
        }

        public GuardarCancionBuilder withData(T data) {
            this.data = data;
            return this;
        }

        public GuardarCancion build() {
            GuardarCancion guardarCancion = new GuardarCancion();
            guardarCancion.repo = this.repo;
            guardarCancion.data = this.data;
            return guardarCancion;
        }
    }
}

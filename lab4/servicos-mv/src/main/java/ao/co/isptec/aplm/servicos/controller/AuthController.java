package ao.co.isptec.aplm.servicos.controller;

import ao.co.isptec.aplm.servicos.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {
    private List<User> users = new ArrayList<>(); // Lista de usuários em memória (substitua por um repositório real)

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Simulação de autenticação
        if ("admin".equals(user.getUsername()) && "1234".equals(user.getPassword())) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        // Adiciona o usuário à lista
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(users); // Retorna a lista de usuários
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        // Criação de um usuário fictício para retornar
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234"); // Não deve ser exposto na prática
        return ResponseEntity.ok(user);
    }
}

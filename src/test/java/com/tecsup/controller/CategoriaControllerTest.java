package com.tecsup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.dto.CategoriaDTO;
import com.tecsup.model.Categoria;
import com.tecsup.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void limpiarDatos() {
        categoriaRepository.deleteAll();
    }

    @Test
    void testGuardarCategoriaEndpoint() throws Exception {
        CategoriaDTO dto = crearCategoriaDTO("Tecnologia", "Productos tecnologicos");

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tecnologia"));
    }

    @Test
    void testListarCategoriasEndpoint() throws Exception {
        categoriaRepository.save(new Categoria(null, "Tecnologia", "Productos tecnologicos"));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tecnologia"));
    }

    @Test
    void testObtenerCategoriaEndpoint() throws Exception {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Tecnologia", "Productos tecnologicos"));

        mockMvc.perform(get("/api/categorias/{id}", categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tecnologia"));
    }

    @Test
    void testObtenerCategoriaEndpointCuandoNoExiste() throws Exception {
        mockMvc.perform(get("/api/categorias/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarCategoriasPorNombreEndpoint() throws Exception {
        categoriaRepository.save(new Categoria(null, "Tecnologia", "Productos tecnologicos"));

        mockMvc.perform(get("/api/categorias/buscar").param("nombre", "tec"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tecnologia"));
    }

    @Test
    void testActualizarCategoriaEndpoint() throws Exception {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Tecnologia", "Productos tecnologicos"));
        CategoriaDTO dto = crearCategoriaDTO("Hogar", "Productos para el hogar");

        mockMvc.perform(put("/api/categorias/{id}", categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Hogar"))
                .andExpect(jsonPath("$.descripcion").value("Productos para el hogar"));
    }

    @Test
    void testActualizarCategoriaEndpointCuandoNoExiste() throws Exception {
        CategoriaDTO dto = crearCategoriaDTO("Hogar", "Productos para el hogar");

        mockMvc.perform(put("/api/categorias/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCategoriaEndpoint() throws Exception {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Tecnologia", "Productos tecnologicos"));

        mockMvc.perform(delete("/api/categorias/{id}", categoria.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/categorias/{id}", categoria.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCategoriaEndpointCuandoNoExiste() throws Exception {
        mockMvc.perform(delete("/api/categorias/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testValidacionNombreVacio() throws Exception {
        CategoriaDTO dto = crearCategoriaDTO("", "Sin nombre");

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    private CategoriaDTO crearCategoriaDTO(String nombre, String descripcion) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        return dto;
    }
}

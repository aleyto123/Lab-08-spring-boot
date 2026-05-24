package com.tecsup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.dto.ProductoDTO;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
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
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void limpiarDatos() {
        productoRepository.deleteAll();
    }

    @Test
    void testGuardarProducto() throws Exception {
        ProductoDTO dto = crearProductoDTO("Mouse", "Accesorios", 80, 20);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    @Test
    void testListarProductos() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void testValidacionNombreVacio() throws Exception {
        ProductoDTO dto = crearProductoDTO("", "Accesorios", 10, 2);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testActualizarProductoEndpoint() throws Exception {
        Producto producto = productoRepository.save(new Producto(null, "Mouse", "Accesorios", 80, 20));
        ProductoDTO dto = crearProductoDTO("Mouse Gamer", "Tecnologia", 120, 15);

        mockMvc.perform(put("/api/productos/{id}", producto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Mouse Gamer"))
                .andExpect(jsonPath("$.categoria").value("Tecnologia"))
                .andExpect(jsonPath("$.precio").value(120))
                .andExpect(jsonPath("$.stock").value(15));
    }

    @Test
    void testActualizarProductoEndpointCuandoNoExiste() throws Exception {
        ProductoDTO dto = crearProductoDTO("Mouse Gamer", "Tecnologia", 120, 15);

        mockMvc.perform(put("/api/productos/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProductoEndpoint() throws Exception {
        Producto producto = productoRepository.save(new Producto(null, "Mouse", "Accesorios", 80, 20));

        mockMvc.perform(delete("/api/productos/{id}", producto.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/productos/{id}", producto.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProductoEndpointCuandoNoExiste() throws Exception {
        mockMvc.perform(delete("/api/productos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    private ProductoDTO crearProductoDTO(String nombre, String categoria, double precio, int stock) {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre(nombre);
        dto.setCategoria(categoria);
        dto.setPrecio(precio);
        dto.setStock(stock);
        return dto;
    }
}

package com.tecsup.repository;

import com.tecsup.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void debeBuscarProductosPorNombreSinImportarMayusculas() {
        productoRepository.save(new Producto(null, "Laptop Gamer", "Tecnologia", 4500.0, 3));
        productoRepository.save(new Producto(null, "Mouse", "Tecnologia", 80.0, 15));

        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase("laptop");

        assertEquals(1, productos.size());
        assertEquals("Laptop Gamer", productos.get(0).getNombre());
        assertEquals("Tecnologia", productos.get(0).getCategoria());
    }
}

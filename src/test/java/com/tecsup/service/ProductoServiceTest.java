package com.tecsup.service;

import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProductos() {
        List<Producto> lista = List.of(
                new Producto(1L, "Monitor", "Tecnologia", 500, 10)
        );

        when(repo.findAll()).thenReturn(lista);

        List<Producto> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Monitor", resultado.get(0).getNombre());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGuardarProducto() {
        Producto producto = new Producto(1L, "Laptop", "Tecnologia", 3000, 5);

        when(repo.save(producto)).thenReturn(producto);

        Producto resultado = service.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        verify(repo).save(producto);
    }

    @Test
    void testObtenerProducto() {
        Producto producto = new Producto(1L, "Teclado", "Tecnologia", 100, 20);

        when(repo.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals("Teclado", resultado.getNombre());
        verify(repo).findById(1L);
    }

    @Test
    void testObtenerProductoCuandoNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = service.obtener(99L);

        assertNull(resultado);
        verify(repo).findById(99L);
    }

    @Test
    void testActualizarProducto() {
        Producto existente = new Producto(1L, "Mouse", "Tecnologia", 80, 20);
        Producto cambios = new Producto(null, "Mouse Gamer", "Accesorios", 120, 15);
        Producto guardado = new Producto(1L, "Mouse Gamer", "Accesorios", 120, 15);

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(existente)).thenReturn(guardado);

        Producto resultado = service.actualizar(1L, cambios);

        assertNotNull(resultado);
        assertEquals("Mouse Gamer", resultado.getNombre());
        assertEquals("Accesorios", resultado.getCategoria());
        assertEquals(120, resultado.getPrecio());
        assertEquals(15, resultado.getStock());
        verify(repo).findById(1L);
        verify(repo).save(existente);
    }

    @Test
    void testActualizarProductoCuandoNoExiste() {
        Producto cambios = new Producto(null, "Mouse Gamer", "Accesorios", 120, 15);

        when(repo.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = service.actualizar(99L, cambios);

        assertNull(resultado);
        verify(repo).findById(99L);
        verify(repo, never()).save(cambios);
    }

    @Test
    void testEliminarProducto() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}

package com.example.appsoa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.appsoa.web.rest.TestUtil;

public class VentaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = new Venta();
        venta1.setId(1L);
        Venta venta2 = new Venta();
        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);
        venta2.setId(2L);
        assertThat(venta1).isNotEqualTo(venta2);
        venta1.setId(null);
        assertThat(venta1).isNotEqualTo(venta2);
    }
}

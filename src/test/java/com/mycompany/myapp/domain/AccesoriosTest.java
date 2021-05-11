package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccesoriosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accesorios.class);
        Accesorios accesorios1 = new Accesorios();
        accesorios1.setId(1L);
        Accesorios accesorios2 = new Accesorios();
        accesorios2.setId(accesorios1.getId());
        assertThat(accesorios1).isEqualTo(accesorios2);
        accesorios2.setId(2L);
        assertThat(accesorios1).isNotEqualTo(accesorios2);
        accesorios1.setId(null);
        assertThat(accesorios1).isNotEqualTo(accesorios2);
    }
}

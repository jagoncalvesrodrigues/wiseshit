package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CamisetasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Camisetas.class);
        Camisetas camisetas1 = new Camisetas();
        camisetas1.setId(1L);
        Camisetas camisetas2 = new Camisetas();
        camisetas2.setId(camisetas1.getId());
        assertThat(camisetas1).isEqualTo(camisetas2);
        camisetas2.setId(2L);
        assertThat(camisetas1).isNotEqualTo(camisetas2);
        camisetas1.setId(null);
        assertThat(camisetas1).isNotEqualTo(camisetas2);
    }
}

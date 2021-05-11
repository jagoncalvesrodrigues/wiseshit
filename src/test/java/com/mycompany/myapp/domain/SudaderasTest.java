package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SudaderasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sudaderas.class);
        Sudaderas sudaderas1 = new Sudaderas();
        sudaderas1.setId(1L);
        Sudaderas sudaderas2 = new Sudaderas();
        sudaderas2.setId(sudaderas1.getId());
        assertThat(sudaderas1).isEqualTo(sudaderas2);
        sudaderas2.setId(2L);
        assertThat(sudaderas1).isNotEqualTo(sudaderas2);
        sudaderas1.setId(null);
        assertThat(sudaderas1).isNotEqualTo(sudaderas2);
    }
}

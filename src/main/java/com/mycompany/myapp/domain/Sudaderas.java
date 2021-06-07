package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sudaderas.
 */
@Entity
@Table(name = "sudaderas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sudaderas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "talla")
    private String talla;

    @Column(name = "color")
    private String color;

    @Column(name = "coleccion")
    private Integer coleccion;

    @ManyToMany(mappedBy = "sudaderas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "camisetas", "sudaderas", "accesorios", "user" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sudaderas id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Sudaderas stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return this.imagen;
    }

    public Sudaderas imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTalla() {
        return this.talla;
    }

    public Sudaderas talla(String talla) {
        this.talla = talla;
        return this;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return this.color;
    }

    public Sudaderas color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getColeccion() {
        return this.coleccion;
    }

    public Sudaderas coleccion(Integer coleccion) {
        this.coleccion = coleccion;
        return this;
    }

    public void setColeccion(Integer coleccion) {
        this.coleccion = coleccion;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public Sudaderas ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Sudaderas addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getSudaderas().add(this);
        return this;
    }

    public Sudaderas removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getSudaderas().remove(this);
        return this;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.removeSudaderas(this));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.addSudaderas(this));
        }
        this.ventas = ventas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sudaderas)) {
            return false;
        }
        return id != null && id.equals(((Sudaderas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sudaderas{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", imagen='" + getImagen() + "'" +
            ", talla='" + getTalla() + "'" +
            ", color='" + getColor() + "'" +
            ", coleccion=" + getColeccion() +
            "}";
    }
}

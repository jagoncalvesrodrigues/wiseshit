package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Camisetas.
 */
@Entity
@Table(name = "camisetas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Camisetas implements Serializable {

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

    @ManyToMany(mappedBy = "camisetas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "camisetas", "sudaderas", "accesorios", "usuario" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Camisetas id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Camisetas stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return this.imagen;
    }

    public Camisetas imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTalla() {
        return this.talla;
    }

    public Camisetas talla(String talla) {
        this.talla = talla;
        return this;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return this.color;
    }

    public Camisetas color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getColeccion() {
        return this.coleccion;
    }

    public Camisetas coleccion(Integer coleccion) {
        this.coleccion = coleccion;
        return this;
    }

    public void setColeccion(Integer coleccion) {
        this.coleccion = coleccion;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public Camisetas ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Camisetas addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getCamisetas().add(this);
        return this;
    }

    public Camisetas removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getCamisetas().remove(this);
        return this;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.removeCamisetas(this));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.addCamisetas(this));
        }
        this.ventas = ventas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Camisetas)) {
            return false;
        }
        return id != null && id.equals(((Camisetas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Camisetas{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", imagen='" + getImagen() + "'" +
            ", talla='" + getTalla() + "'" +
            ", color='" + getColor() + "'" +
            ", coleccion=" + getColeccion() +
            "}";
    }
}

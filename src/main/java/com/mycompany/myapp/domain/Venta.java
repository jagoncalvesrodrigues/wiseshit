package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_venta__camisetas",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "camisetas_id")
    )
    @JsonIgnoreProperties(value = { "ventas" }, allowSetters = true)
    private Set<Camisetas> camisetas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_venta__sudaderas",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "sudaderas_id")
    )
    @JsonIgnoreProperties(value = { "ventas" }, allowSetters = true)
    private Set<Sudaderas> sudaderas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_venta__accesorios",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "accesorios_id")
    )
    @JsonIgnoreProperties(value = { "ventas" }, allowSetters = true)
    private Set<Accesorios> accesorios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "posts", "ventas" }, allowSetters = true)
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta id(Long id) {
        this.id = id;
        return this;
    }

    public Double getImporte() {
        return this.importe;
    }

    public Venta importe(Double importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Venta fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Set<Camisetas> getCamisetas() {
        return this.camisetas;
    }

    public Venta camisetas(Set<Camisetas> camisetas) {
        this.setCamisetas(camisetas);
        return this;
    }

    public Venta addCamisetas(Camisetas camisetas) {
        this.camisetas.add(camisetas);
        camisetas.getVentas().add(this);
        return this;
    }

    public Venta removeCamisetas(Camisetas camisetas) {
        this.camisetas.remove(camisetas);
        camisetas.getVentas().remove(this);
        return this;
    }

    public void setCamisetas(Set<Camisetas> camisetas) {
        this.camisetas = camisetas;
    }

    public Set<Sudaderas> getSudaderas() {
        return this.sudaderas;
    }

    public Venta sudaderas(Set<Sudaderas> sudaderas) {
        this.setSudaderas(sudaderas);
        return this;
    }

    public Venta addSudaderas(Sudaderas sudaderas) {
        this.sudaderas.add(sudaderas);
        sudaderas.getVentas().add(this);
        return this;
    }

    public Venta removeSudaderas(Sudaderas sudaderas) {
        this.sudaderas.remove(sudaderas);
        sudaderas.getVentas().remove(this);
        return this;
    }

    public void setSudaderas(Set<Sudaderas> sudaderas) {
        this.sudaderas = sudaderas;
    }

    public Set<Accesorios> getAccesorios() {
        return this.accesorios;
    }

    public Venta accesorios(Set<Accesorios> accesorios) {
        this.setAccesorios(accesorios);
        return this;
    }

    public Venta addAccesorios(Accesorios accesorios) {
        this.accesorios.add(accesorios);
        accesorios.getVentas().add(this);
        return this;
    }

    public Venta removeAccesorios(Accesorios accesorios) {
        this.accesorios.remove(accesorios);
        accesorios.getVentas().remove(this);
        return this;
    }

    public void setAccesorios(Set<Accesorios> accesorios) {
        this.accesorios = accesorios;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Venta usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", importe=" + getImporte() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}

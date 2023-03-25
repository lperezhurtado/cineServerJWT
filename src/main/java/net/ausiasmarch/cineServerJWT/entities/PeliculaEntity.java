package net.ausiasmarch.cineServerJWT.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pelicula")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PeliculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private int year;
    private int duracion; //en minutos
    private String director;
    private String sinopsis;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fechaAlta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fechaBaja;

    private boolean versionNormal;
    private boolean versionEspecial;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_genero")
    private GeneroEntity genero;

    private String imagen;

    @OneToMany(mappedBy = "pelicula" , fetch = FetchType.LAZY)
    @JsonIgnore
    private final List<SesionEntity> sesiones;

    public PeliculaEntity() {
        this.sesiones = new ArrayList<>();
    }

    public PeliculaEntity(Long id) {
        this.sesiones = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getyear() {
        return year;
    }

    public void setyear(int year) {
        this.year = year;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public boolean isVersionNormal() {
        return versionNormal;
    }

    public void setVersionNormal(boolean versionNormal) {
        this.versionNormal = versionNormal;
    }

    public boolean isVersionEspecial() {
        return versionEspecial;
    }

    public void setVersionEspecial(boolean versionEspecial) {
        this.versionEspecial = versionEspecial;
    }

    public GeneroEntity getGenero() {
        return genero;
    }

    public void setGenero(GeneroEntity genero) {
        this.genero = genero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getSesionesCount() {
        return sesiones.size();
    }

}

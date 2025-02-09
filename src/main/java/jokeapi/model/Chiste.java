package jokeapi.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Pepinho on 21/10/15.
 * <p>
 * Clase que representa un chiste.
 * Atributos: id de tipo int, categoria de tipo Categoria, idiomade tipo Lenguaje, tipo de TipoChiste,
 *  List<Flag> banderas, String chiste, String respuesta.
 */
@Entity
@NamedQuery(name = "getAllIds", query = "SELECT id FROM Chiste ORDER BY id")
public class Chiste implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num_chiste")
    private int idChiste;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private TipoChiste tipo;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "chiste_flags", joinColumns = @JoinColumn(name = "chiste_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "flag")
    private final List<Flag> banderas = new ArrayList<>();

    @Transient
    private String chiste;

    @Transient
    private String respuesta;

    @Enumerated(EnumType.STRING)
    private Lenguaje lenguaje;

    @Transient
    private final String SEPARATOR = "\n";

    @Access(AccessType.PROPERTY)
    @Column(name = "chiste_completo", length = 2048)
    public String getChisteCompleto(){
        if (respuesta != null)
            return chiste + SEPARATOR + respuesta;
        else
            return chiste;
    }

    public void setChisteCompleto(String chisteCompleto) {
        if (chisteCompleto != null) {
            String[] partes = chisteCompleto.split(SEPARATOR);
            this.chiste = partes.length > 0 ? partes[0] : null;
            this.respuesta = partes.length > 1 ? partes[1] : null;
        }
    }

    /**
     * Constructor de la clase Chiste.
     * @param id Identificador del chiste
     * @param categoria Categoría del chiste
     * @param idioma Idioma del chiste
     * @param tipo Tipo del chiste
     * @param chiste Chiste
     * @param respuesta Respuesta del chiste
     */
    public Chiste(int id, Categoria categoria, String idioma, TipoChiste tipo, String chiste, String respuesta) {
        this.idChiste = id;
        this.categoria = categoria;
        this.tipo = tipo;
        this.chiste = chiste;
        this.respuesta = respuesta;
        this.lenguaje = Lenguaje.getLenguaje(idioma);
    }

    /**
     * Constructor por defecto de la clase Chiste.
     *
     */
    public Chiste() {
//        this.id = 0;
        this.categoria = Categoria.ANY;
        this.lenguaje = Lenguaje.EN;
        this.tipo = TipoChiste.SINGLE;
        this.chiste = "";
        this.respuesta = "";
    }

    /**
     * Devuelve el identificador del chiste.
     * @return Identificador del chiste
     */
    public int getIdChiste() {
        return idChiste;
    }

    /**
     * Establece el identificador del chiste.
     * @param idChiste Identificador del chiste
     */
    public void setIdChiste(int idChiste) {
        this.idChiste = idChiste;
    }

    /**
     * Devuelve la categoría del chiste.
     * @return Categoría del chiste
     */
    public Categoria getCategoria() {
        return categoria;
    }

    public String getCategoriaString() {
        return categoria.getNombre();
    }

    /**
     * Establece la categoría del chiste.
     * @param categoria Categoría del chiste
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = Categoria.getCategoria(categoria);
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public String getLenguajeString() {
        return lenguaje.getLenguaje();
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = Lenguaje.getLenguaje(lenguaje);
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    /**
     * Devuelve el tipo del chiste.
     * @return Tipo del chiste
     */
    public TipoChiste getTipo() {
        return tipo;
    }

    public String getTipoString() {
        return tipo.getNombre();
    }

    /**
     * Establece el tipo del chiste.
     * @param tipo Tipo del chiste
     */
    public void setTipo(TipoChiste tipo) {
        this.tipo = tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = TipoChiste.getTipoChiste(tipo);
    }

    /**
     * Devuelve las banderas del chiste.
     * @return Banderas del chiste
     */
    public List<Flag> getBanderas() {
        return banderas;
    }

    /**
     * Añade una bandera al chiste.
     * @param flag Bandera a añadir
     */
    public void addFlag(Flag flag) {
        banderas.add(flag);
    }

    public void addFlag(String flag) {
        banderas.add(Flag.getFlag(flag));
    }
    public void setBanderas(List<Flag> list){
        list.forEach(f -> addFlag(f));
    }
    public boolean removeFlag(Flag bandera) {
        return banderas.remove(bandera);
    }

    /**
     * Si el chiste tiene esa bandera, devuelve true.
     * @param bandera Bandera a comprobar
     * @return  true si el chiste tiene esa bandera, false en caso contrario
     */
    public boolean containsFlag(Flag bandera) {
        return banderas.contains(bandera);
    }


    /**
     * Devuelve el chiste como cadena de caracteres.
     * @return Chiste como String
     */
    public String getChiste() {
        return chiste;
    }

    /**
     * Establece el chiste.
     * @param chiste Chiste
     */
    public void setChiste(String chiste) {
        this.chiste = chiste;
    }

    /**
     * Devuelve la respuesta del chiste.
     * @return Respuesta del chiste
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta del chiste.
     * @param respuesta Respuesta del chiste
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public boolean equals(Object o) {
        boolean iguais;
        if (this == o) {
            iguais = true;
        } else if (o == null || getClass() != o.getClass()) {
            iguais = false;
        } else {
            Chiste chiste = (Chiste) o;
            iguais = idChiste == chiste.idChiste;
        }
        return iguais;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idChiste);
    }

    /**
     * Sobrescritura del método toString() para que devuelva el chiste.
     * Lo devuelve empleando un StringBuilder y por medio del método forEach() para recorrer la lista de banderas.
     * @return Chiste como String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chiste: ").append(chiste).append(System.lineSeparator());
        sb.append("Respuesta: ").append(respuesta).append(System.lineSeparator());
        sb.append("Categoría: ").append(categoria).append(System.lineSeparator());
        sb.append("Idioma: ").append(lenguaje).append(System.lineSeparator());
        sb.append("Tipo: ").append(tipo).append(System.lineSeparator());
        sb.append("Banderas: ");
        banderas.forEach(b -> sb.append(b).append(" "));
        sb.append(System.lineSeparator());
        return sb.toString();
    }

}

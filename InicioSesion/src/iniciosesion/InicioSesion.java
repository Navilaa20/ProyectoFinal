/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iniciosesion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author nilve
 */
public class InicioSesion {

        public static List<Usuario> listaUsuarios = new ArrayList<>();
        public static List<Eleccion> listaElecciones = new ArrayList<>();
        public static List<Candidatos> listaCandidatos = new ArrayList <>();
        public static List<Votante> listaVotantes = new ArrayList <>();
        public static List<Voto> listaVotos = new ArrayList <>();
        public static final String archivoUsuarios = "Usuarios.txt"; 
        public static final String archivoElecciones = "Elecciones.txt";
        public static final String archivoCandidatos = "Candidatos.txt";
        public static final String archivoVotantes = "Votantes.txt";
        public static final String archivoVotos = "votos.txt";
        public static List<Usuario> listaUsuariosTemporales = new ArrayList<>();
          
    public static void main(String[] args) {
    Scanner scan = new Scanner (System.in);
    cargarUsuariosDesdeArchivo();
    cargarVotantesDesdeArchivo();
    cargarEleccionesDesdeArchivo();
    cargarCandidatosDesdeArchivo();
    
        System.out.println("¿Como desea iniciar sesión?");      
        System.out.println("1. Administrador \n2. Usuario \n3. Votante");
        int opcion = scan.nextInt();
        
            switch (opcion) {
                case 1:
                    loginAdministrador();
                    break;
                case 2:
                    loginUsuario();
                    break; 
                case 3: 
                    loginVotante();
                default:
                    System.out.println("Opción invalida");
                    break;
            }
    } 
    
    public static void loginAdministrador(){
        Scanner scan = new Scanner (System.in);
        
        String contraseniaAdminUno= "Admin123";
        String contraseniaAdmon;
        
        System.out.println("*********************");
        System.out.println("Sistema de votaciones");
        System.out.println("*********************");
        
        do {
            System.out.println("");
            System.out.println("Ingrese contraseña para el usuario admin:");
            contraseniaAdmon = scan.nextLine();
            
            if (contraseniaAdminUno.compareTo(contraseniaAdmon)==0){
                System.out.println("");
                System.out.println("La contraseña es correcta");
                System.out.println("");
                System.out.println("Inicio de sesión exitoso");
                
                registroUsuarios(); //Llamamos al metodo de registro de usuarios
            }
            else {
                System.out.println("");
                System.out.println("La constraseña es incorrecta");
                System.out.println("");
                System.out.println("Ingrese contraseña de nuevo");                 
            }       
            }while (contraseniaAdminUno.compareTo(contraseniaAdmon) !=0);
}
    
    public static void loginUsuario(){
        Scanner scan = new Scanner (System.in);
        System.out.println("Ingrese correo de usuario");
        String correoUsuario = scan.nextLine();
        System.out.println("Ingrese contraseña de usuario");
        String contraseña = scan.nextLine(); 
        
        var usuarioLogeado=loginUsuario(correoUsuario, contraseña);
        if (usuarioLogeado != null){
            mostrarMenu(usuarioLogeado.getRol()); 
        } else{
            System.out.println("Usuario incorrecto");
        }
    }
    
    public static Usuario loginUsuario(String correoUsuario, String contraseña) {
    return InicioSesion.listaUsuarios.stream()
            .filter(usuario -> usuario.getCorreo().equalsIgnoreCase(correoUsuario) && usuario.getContraseña().equals(contraseña))
            .findFirst()
            .orElse(null);
}
    
    public static void loginVotante(){
        Scanner scan = new Scanner (System.in);
        System.out.println("Inicio de Sesion para Votantes");
        System.out.println("Ingrese correo de Votante");
        String correoVotante = scan.nextLine();
        System.out.println("Ingrese su contraseña");
        String contraseniaVotante = scan.nextLine();
        System.out.println("");
        System.out.println("Ingrese CUI: ");
        long cui = scan.nextLong();
            
        var votanteLogeado = loginVotante(correoVotante,contraseniaVotante,cui);
        if (votanteLogeado != null){
            System.out.println("Inicio de sesión exitoso para el votante: ");
            menuVotantes();
        } else {
            System.out.println("Inicio de sesion incorrecto");
        }
        
        
    }
    
    public static Votante loginVotante(String correoVotante, String contraseniaVotante, long cui) {
    if (InicioSesion.listaVotantes != null) {
        return InicioSesion.listaVotantes.stream()
            .filter(votante -> votante != null &&
                               votante.getCorreoVotante() != null &&
                               votante.getCorreoVotante().equals(correoVotante) &&
                               votante.getContraseniaVotante() != null &&
                               votante.getContraseniaVotante().equals(contraseniaVotante) &&
                               votante.getCui() == cui && 
                               votante.getEstado().equalsIgnoreCase("Activo")) 
            .findFirst()
            .orElse(null);
    } else {
        return null;
    }
}
    
    public static void registroUsuarios (){
        System.out.println("Bienvenido al Sistema de votaciones");
        System.out.println("Ahora estas en la sección");
        System.out.println("**Registro de Usuarios**");

        Scanner scan = new Scanner(System.in);
        int opcion;

        while (true) {
            System.out.println("Elige una opción");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Lista de usuarios");
            System.out.println("3. Editar usuario");
            System.out.println("4. Salir");
            System.out.print("Selecciona la opción: ");
            
            try {
                opcion = scan.nextInt();
                scan.nextLine();
            } catch (java.util.InputMismatchException e){
                System.err.println("Opción invalida, debes ingresar un número. ");
                scan.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarUsuario(scan);
                    break;

                case 2:
                    listarUsuarios();
                    break;

                case 3:
                    editarUsuario(scan);
                    break;

                case 4:
                    System.out.println("Saliendo del sistema de votaciones.");
                    System.exit(0);
                    break;

                default:
                    System.err.println("Opción inválida. Ingresa de nuevo.");
            }
        }
    }

    private static void cargarUsuariosDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String nombre = parts[0];
                String apellido = parts[1];
                String correo = parts[2];
                String contraseña = parts[3];
                String rol = parts[4];
                String estado = parts [5];
                listaUsuariosTemporales.add(new Usuario(nombre, apellido, correo, contraseña, rol, estado));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listaUsuarios = listaUsuariosTemporales.stream().filter(usuario-> usuario.getEstado().equalsIgnoreCase("Activo")).collect(Collectors.toList());
        listaUsuariosTemporales.clear();
    }

    private static void guardarUsuariosEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoUsuarios))) {
            for (Usuario usuario : listaUsuarios) {
                bw.write(usuario.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void agregarUsuario(Scanner scan) {
        System.out.print("Ingrese nombres del usuario: ");
        String nombre = scan.nextLine();
        System.out.print("Ingrese apellidos del usuario: ");
        String apellido = scan.nextLine();
        System.out.print("Ingrese correo del usuario: ");
        String correo = scan.nextLine();
        System.out.print("Ingrese contraseña del usuario: ");
        String contraseña = scan.nextLine();
        System.out.print("¿Qué rol tendrá este usuario? ");
        String rol = scan.nextLine();
        System.out.print("En que estado se encuentra el usuario ");
        String estado = scan.nextLine();
        

        Usuario nuevoUsuario = new Usuario(nombre, apellido, correo, contraseña, rol, estado);
        listaUsuarios.add(nuevoUsuario);
        guardarUsuariosEnArchivo();
        System.out.println("Usuario agregado exitosamente.");
    }

    private static void listarUsuarios() {
        System.out.println("Lista de usuarios:");
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario.ToString());
        }
    }

    private static void editarUsuario(Scanner scan) {
        System.out.print("Ingrese el correo del usuario que desea editar: ");
        String correo = scan.nextLine();

        Usuario usuarioAEditar = null;
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                usuarioAEditar = usuario;
                break;
            }
        }

        if (usuarioAEditar != null) {
            System.out.print("Nuevo nombre del usuario: ");
            String nuevoNombre = scan.nextLine();
            usuarioAEditar.setNombre(nuevoNombre);
            System.out.print("Nuevo apellido del usuario: ");
            String nuevoApellido = scan.nextLine();
            usuarioAEditar.setApellido(nuevoApellido);
            System.out.print("Nuevo correo del usuario: ");
            String nuevoCorreo = scan.nextLine();
            usuarioAEditar.setCorreo(nuevoCorreo);
            System.out.print("Nueva contraseña del usuario: ");
            String nuevaContraseña = scan.nextLine();
            usuarioAEditar.setContraseña(nuevaContraseña);
            System.out.print("Nuevo rol: ");
            String nuevoRol = scan.nextLine();
            usuarioAEditar.setRol(nuevoRol);
            System.out.println("Estado del usuario");
            String nuevoEstado = scan.nextLine();
            usuarioAEditar.setEstado(nuevoEstado);
            
            guardarUsuariosEnArchivo();
            listaUsuarios.clear();
            cargarUsuariosDesdeArchivo();
            

            System.out.println("Usuario editado exitosamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    public static void mostrarMenu(String rol){                  
        switch (rol){
            case "Administrador": menuAdministrador();
            break;
            
            case "Registrador de Votantes": menuRegistroVotantes();
            break; 
            
            case "Auditor": menuReportes();   
            break;
            
            default: 
                System.out.println("Opción invalida");
        }
               
    }
    
    public static void menuAdministrador(){
        Scanner scan = new Scanner (System.in);
        System.out.println("Sistema de votaciones");
        System.out.println("");
        System.out.println("Administración de Elecciones");
        System.out.println("1. Gestionar Elecciones");
        System.out.println("2. Gestionar Candidatos");
        System.out.println("3. Reportes de votaciones");
        
        System.out.print("Ingrese una opción: ");
        int opcion = scan.nextInt();
        
        switch (opcion){
            case 1: 
                gestionarElecciones();
                break;
            case 2: 
                gestionarCandidatos();
                break;
            case 3: 
                menuReportes();
                
            default: 
                System.out.println("Ingrese una opción válida");
        } 
        
    }
    
    private static void gestionarElecciones(){
        Scanner scan = new Scanner(System.in);        
        System.out.println("¿Cuál es el título de las elecciones?");
        String titulo = scan.nextLine();
        System.out.println("¿Cuál es el propósito de las elecciones?");
        String proposito = scan.nextLine();       
        System.out.println("¿Cuál es la descripción de las elecciones?");
        String descripcion = scan.nextLine();       
        System.out.println("¿Cuál será el código de las elecciones?");
        int codigoUnico = scan.nextInt();    
        scan.nextLine();
        System.out.println("¿Cuál es la fecha de incio de las votaciones? (Formato AAAA-MM-DD)");
        String fechaInicioStr = scan.nextLine();
        System.out.println("¿Cuál es la fecha de fin de las votaciones? (Formato AAAA-MM-DD)");
        String fechaFinStr = scan.nextLine();
        
        //analiza las cadenas de fechas en objetos del LocalDate ustando DataTime y los formatea
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
              
        Eleccion nuevaGestion = new Eleccion (titulo, proposito, descripcion,codigoUnico, fechaInicio, fechaFin);
        listaElecciones.add(nuevaGestion);
        guardarEleccionesEnArchivo();
        System.out.println("Gestión realizada correcatemente");
    }
    
    private static void cargarEleccionesDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoElecciones))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            String titulo = parts[0];
            String proposito = parts[1];
            String descripcion = parts[2];
            int codigoUnico = Integer.parseInt(parts [3]);
            LocalDate fechaInicio = LocalDate.parse(parts[4]); // Asume que la fecha se guarda en un formato válido
            LocalDate fechaFin = LocalDate.parse(parts[5]); // Asume que la fecha se guarda en un formato válido
            listaElecciones.add(new Eleccion(titulo, proposito, descripcion, codigoUnico, fechaInicio, fechaFin));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private static void guardarEleccionesEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoElecciones))) {
            for (Eleccion eleccion : listaElecciones) {
                bw.write(eleccion.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void gestionarCandidatos(){
        Scanner scan = new Scanner(System.in);
        int opciones;
        
        System.out.println("Gestión de candidatos");
        System.out.println();
        
        while (true) {              
            System.out.println("1. Agregar Candidato");
            System.out.println("2. Listar Candidatos");
            System.out.println("3. Editar Candidato");
            System.out.println("4. Eliminar Candidato");
            System.out.println("Seleccione una opción");
        
        try {
            opciones = scan.nextInt();
            scan.nextLine();

            } catch (java.util.InputMismatchException e){
            System.err.println("Opción invalida, debes ingresar un número. ");
            scan.nextLine();
            continue;
            }

            switch (opciones) { 

                case 1: 
                    agregarCandidato(scan);
                    break;
                case 2:
                    listarCandidato();
                    break;
                case 3:
                    editarCandidato(scan);
                    break;
                case 4:
                    eliminarCandidato(scan);
            }
        }
    } 
    
    private static void agregarCandidato(Scanner scan){
        System.out.println("Agregar Candidatos");
        System.out.println("");
        System.out.println("Ingrese nombre: ");
        String nombreCandidato = scan.nextLine();
        System.out.println("Ingrese formación: ");
        String formacion = scan.nextLine();
        System.out.println("Ingrese experiencia laboral: ");
        String experiencia = scan.nextLine();
        System.out.println("Ingrese Código Unico de Identificación (CUI): ");
        int codigo = scan.nextInt();
        System.out.println("Ingrese en que Elección puede participar");
        String puesto = scan.nextLine();
        
        Candidatos nuevoCandidato = new Candidatos(nombreCandidato, formacion, experiencia, codigo, puesto);
        listaCandidatos.add(nuevoCandidato);
        guardarCandidatosEnArchivo();
        System.out.println("Usuario agregado exitosamente.");
        
    }
            
    private static void cargarCandidatosDesdeArchivo(){
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCandidatos))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5){ 
                String nombre = parts[0];
                String formacion = parts[1];
                String experiencia = parts[2];
                int codigo = Integer.parseInt(parts [3].trim());
                String puesto = parts[4];
                listaCandidatos.add(new Candidatos(nombre, formacion, experiencia, codigo, puesto));
            } else {
                System.out.println("Error: formato incorrencto en línea -"+line);
            }    
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    private static void guardarCandidatosEnArchivo(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoCandidatos))) {
            for (Candidatos candidatos : listaCandidatos) {
                bw.write(candidatos.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    private static void listarCandidato (){
        System.out.println("Listado de candidatos");
        for (Candidatos candidatos : listaCandidatos){
            System.out.println(candidatos.toString());
        }
    }
    
    private static void editarCandidato (Scanner scan){
        System.out.println("Ingrese el CUI del candidato que desea editar:");
        int codigo = scan.nextInt();
        scan.nextLine();
        
        Candidatos candidatosAEditar = null;
        for (Candidatos candidatos : listaCandidatos){
            if (candidatos.getCodigo() == codigo){
                candidatosAEditar = candidatos;
                break;
            }
        }
        
        if (candidatosAEditar != null){
            System.out.println("Puede editar al candidato");
            System.out.println("");
            
            System.out.println("Ingrese nombre: ");
            String nuevoNombreCandidato = scan.nextLine();
            candidatosAEditar.setNombreCandidato(nuevoNombreCandidato);
            scan.nextLine();
            System.out.println("Ingrese formación: ");
            String nuevaFormacion = scan.nextLine();
            candidatosAEditar.setFormacion(nuevaFormacion);
            scan.nextLine();
            System.out.println("Ingrese experiencia laboral: ");
            String nuevaExperiencia = scan.nextLine();
            candidatosAEditar.setExperiencia(nuevaExperiencia);
            scan.nextLine();
            System.out.println("Ingrese Código Unico de Identificación (CUI): ");
            int nuevoCodigo = scan.nextInt();
            candidatosAEditar.setCodigo(nuevoCodigo);
            scan.nextLine();
            System.out.println("Ingrese en que Elección puede participar");
            String nuevoPuesto = scan.nextLine();
            candidatosAEditar.setPuesto(nuevoPuesto);
            
            guardarCandidatosEnArchivo();
            listaCandidatos.clear();
            cargarCandidatosDesdeArchivo();
            
            System.out.println("El candidato a sido editado exitosamente");
        } else {
            System.out.println("Usuario no encontrado");
        }
    }
    
    private static void eliminarCandidato(Scanner scan){
        System.out.println("Ingrese el CUI del candidato que desea eliminar:");
        int codigo = scan.nextInt();

        Candidatos candidatoAEliminar = null;
        for (Candidatos candidatos : listaCandidatos){
            if (candidatos.getCodigo()== codigo){
                candidatoAEliminar = candidatos;
                break;
            }
        }
        if (candidatoAEliminar != null){
            listaCandidatos.remove(candidatoAEliminar);
            System.out.println("Candidato eliminado correctamente");
        } else {
            System.out.println("Candidato no encontrado. ");
        }
        
        guardarCandidatosEnArchivo();
    }
    
    public static void menuRegistroVotantes(){
        Scanner scan = new Scanner (System.in);
        
        System.out.println("Sistema de votaciones");
        System.out.println("");
        System.out.println("Registro de votantes");
        System.out.println("1. Agregar Votante \n2. Modificar Votante \n3. Reinicar contraseña de votante");
        
        System.out.print("Ingrese una opción: ");
        int opcion = scan.nextInt();
        
        switch (opcion){
            case 1:
                agregarVotante(scan);
                break;
            case 2: 
                modificarVotante(scan);
                break;
            case 3:
                reiniciarContrasenia(scan);
                break;
            default: 
                System.out.println("La opcion es incorrecta");
        }     
    }
    
    private static void agregarVotante(Scanner scan){
        System.out.println("Menu de agregar votantes");
        System.out.println("");
        scan.nextLine();
        System.out.print("Ingrese correo electrónico: ");
        String correo = scan.nextLine();
        System.out.print("Ingrese nombres: ");
        String nombres = scan.nextLine();
        System.out.print("Ingrese apellidos: ");
        String apellidos = scan.nextLine();
        System.out.print("Ingrese CUI: ");
        long cui = scan.nextLong();
        scan.nextLine();
        System.out.print("Ingrese Sexo: ");
        String sexo = scan.nextLine();
        scan.nextLine();
        System.out.print("Ingrese Fecha de nacimiento: (AAAA-MM-DD)");
        String fechaNacimientoStr = scan.nextLine();
        //Calcular edad
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        LocalDate fechaActual = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, fechaActual);
        if (edad.getYears()< 18){
            System.out.println("El votante debe ser mayor de 18 años.");
            return;
        }
        System.out.print("Ingrese dirección de residencia: ");
        String direccion = scan.nextLine();
        System.out.print("Ingrese departamento de residencia: ");
        String departamento = scan.nextLine();
        System.out.print("Ingrese municipio de residencia: ");
        String municipio = scan.nextLine();
        System.out.print("Ingrese país de residencia: ");
        String pais = scan.nextLine();
        scan.nextLine();
        String contrasenia = generarContraseniaAleatoria(16);
        System.out.println("La contraseña es: " + contrasenia);
        System.out.println("Estado del votante (Activo o Inactivo)");
        String estado = scan.nextLine();
        
        Votante nuevoVotante = new Votante (correo, nombres, apellidos, cui, sexo, LocalDate.parse(fechaNacimientoStr), direccion, departamento, municipio, pais, contrasenia, estado);
        listaVotantes.add(nuevoVotante);
        guardarVotantesEnArchivo();
        System.out.println("Votante registrado. ");
        
    }
    
    private static String generarContraseniaAleatoria(int longitud){
        String caracteres = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuuiopasdfghjklzxcvbnm0123456789";
        StringBuilder contrasenia = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < longitud; i++){
            int indice = random.nextInt(caracteres.length());
            contrasenia.append(caracteres.charAt(indice));
        }
            return contrasenia.toString();
    }
    
    private static void cargarVotantesDesdeArchivo(){
        try (BufferedReader br = new BufferedReader(new FileReader(archivoVotantes))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 11){
                String correoVotante = parts [0];
                String nombres = parts [1];
                String apellidos = parts [2];
                long cui = Long.parseLong(parts[3]);
                String sexo = parts [4];
                String fechaNacimientoStr = parts [5];
                String direccion = parts [6];
                String departamento = parts [7];
                String municipio = parts [8];
                String pais = parts [9];
                String contraseniaVotante = parts [10];
                String estado = parts [11];
                listaVotantes.add(new Votante(correoVotante,nombres,apellidos,cui,sexo,LocalDate.parse(fechaNacimientoStr),direccion,departamento,municipio,pais,contraseniaVotante,estado));
            } else {
                System.out.println("Error: formato incorrencto en línea -"+line);
            }
        }
    } catch (IOException e) {
      e.printStackTrace();    
    }
    }
   
    private static void guardarVotantesEnArchivo(){
    try (BufferedWriter bw = new BufferedWriter(new FileWriter (archivoVotantes))){
        for (Votante votante : listaVotantes){
            bw.write(votante.toCsv());
            bw.newLine();
        }
    } catch (IOException e){
        e.printStackTrace();
    }
}

    private static void modificarVotante(Scanner scan){
        System.out.println("Ingrese el CUI del votante que desea editar: ");
        Long cui = scan.nextLong();
        
        Votante votanteAEditar = null;
        for (Votante votante : listaVotantes){
            if(votante.getCui()== cui){
                votanteAEditar = votante;
                break;
            }
        }
        
        if (votanteAEditar != null){
            System.out.println("Puede editar al votante: " + votanteAEditar);
            System.out.println("");
            
            System.out.println("Ingrese nuevo correo electrónico: ");
            String nuevoCorreo = scan.nextLine();
            votanteAEditar.setCorreoVotante(nuevoCorreo);
            System.out.println("Ingrese nueva dirección: ");
            String nuevaDireccion = scan.nextLine();
            votanteAEditar.setDireccion(nuevaDireccion);
            System.out.println("Ingrese nuevo Pais de residencia: ");
            String nuevoPais = scan.nextLine();
            votanteAEditar.setPais(nuevoPais);
            System.out.println("Ingrese nuevo Departamento de residencia: ");
            String nuevoDepartamento = scan.nextLine();
            votanteAEditar.setDepartamento(nuevoDepartamento);
            System.out.println("Ingrese nuevo Municipio de residencia: ");
            String nuevoMunicipio = scan.nextLine();
            votanteAEditar.setMunicipio(nuevoMunicipio);
            
            guardarVotantesEnArchivo();
            listaCandidatos.clear();
            cargarVotantesDesdeArchivo();
        }
    }
    
    private static void reiniciarContrasenia(Scanner scan){
        System.out.println("Ingrese el CUI del votante que desea reinicar: ");
        long cui = scan.nextLong();
        
        Votante votanteAReiniciar = null;
        for (Votante votante : listaVotantes){
            if (votante.getCui()== cui){
                votanteAReiniciar = votante;
                break;
            }
        }
        
        if (votanteAReiniciar != null){
            String nuevaContrasenia = generarContraseniaAleatoria(16); //genera la nueva contraseña
            votanteAReiniciar.setContraseniaVotante(nuevaContrasenia); // actualiza la contraseña
            guardarVotantesEnArchivo();
            System.out.println("La contraseña del votante ha sido reiniciada con éxito: ");
            System.out.println("La nueva contraseña es: "+ nuevaContrasenia);
        } else {
            System.out.println("Votante no encontrado, verifique el CUI e intentelo de nuevo.");
        }
    }
    
    public static void menuVotantes(){
        Scanner scan = new Scanner (System.in);
        
        System.out.println("Sistema de votaciones");
        System.out.println();
        
        for (Eleccion eleccion : listaElecciones){
            System.out.println("Titulo: "+eleccion.getTitulo());
            System.out.println("Descripción: "+ eleccion.getDescripcion());
            System.out.println("Propósito: " +eleccion.getProposito());
            System.out.println("Código: " + eleccion.getCodigoUnico());
            System.out.println();
        }
        
        System.out.println("1. Elección Presidente \n2. Elección Diputados \n3. Elección Alcalde");
        
        System.out.print("¿Qué elección deseas realizar?");
        int opcion = scan.nextInt();
        
        switch (opcion){
            case 1: 
                eleccionPresidente();
                break;
            case 2:
                eleccionDiputados();
                break;
            case 3: 
                eleccionAlcalde();
                break;
                
            default: 
                System.out.println("Opcion incorrecta");
        } 
    }
    
    public static void eleccionPresidente(){
        Scanner scan = new Scanner (System.in);
        
        System.out.println("Elección para Presidente");
        System.out.println("Candidatos disponibles");
        
        List<Candidatos> candidatosPresidente = listaCandidatos.stream()
                .filter(candidato -> candidato.getPuesto().equalsIgnoreCase("Presidente"))
                .collect(Collectors.toList());
        if (candidatosPresidente.isEmpty()){
            System.out.println("No hay candidatos diponibles para esta elección");
            return;
        } 
        int i = 1;
        for (Candidatos candidato : candidatosPresidente){
            System.out.println(i + ". " + candidato.getNombreCandidato());
            i++;
        }
            
        System.out.println("Selecciona el número del candidato por el que deseas votar: ");
        System.out.println("Presiona 0 para volver al menu anterior");
        int opcion = scan.nextInt();
        
        Votante votante = loginVotante(archivoVotantes, archivoVotantes, i);
            
        if (opcion == 0){
            menuVotantes();
        } else if (opcion > 0 && opcion <= candidatosPresidente.size()){
            Candidatos candidatoSeleccionado = candidatosPresidente.get(opcion - 1);                
            Voto voto = new Voto (votante, candidatoSeleccionado);
            listaVotos.add(voto);
                
            System.out.println("Has votado por: " + candidatoSeleccionado.getNombreCandidato());
                
        } else {
            System.out.println("Opcion incorrecta. ");
            eleccionPresidente(); 
            }
        }

    private static void eleccionDiputados() {
        Scanner scan = new Scanner (System.in);
        
        System.out.println("Elección para Diputados");
        System.out.println("Candidatos disponibles");
        
        List<Candidatos> candidatosDiputados = listaCandidatos.stream()
                .filter(candidato -> candidato.getPuesto().equalsIgnoreCase("Diputados"))
                .collect(Collectors.toList());
        if (candidatosDiputados.isEmpty()){
            System.out.println("No hay candidatos diponibles para esta elección");
            return;
        } 
        int i = 1;
        for (Candidatos candidato : candidatosDiputados){
            System.out.println(i + ". " + candidato.getNombreCandidato());
            i++;
        }
            
        System.out.println("Selecciona el número del candidato por el que deseas votar: ");
        System.out.println("Presiona 0 para volver al menu anterior");
        int opcion = scan.nextInt();
        
        Votante votante = loginVotante(archivoVotantes, archivoVotantes, i);
            
        if (opcion == 0){
            menuVotantes();
        } else if (opcion > 0 && opcion <= candidatosDiputados.size()){
            Candidatos candidatoSeleccionado = candidatosDiputados.get(opcion - 1);                
            Voto voto = new Voto (votante, candidatoSeleccionado);
            listaVotos.add(voto);
                
            System.out.println("Has votado por: " + candidatoSeleccionado.getNombreCandidato());
                
        } else {
            System.out.println("Opcion incorrecta. ");
            eleccionPresidente(); 
            }
        }
    
    public static void eleccionAlcalde(){
        Scanner scan = new Scanner (System.in);
        
        System.out.println("Elección para Alcaldes");
        System.out.println("Candidatos disponibles");
        
        List<Candidatos> candidatosAlcalde = listaCandidatos.stream()
                .filter(candidato -> candidato.getPuesto().equalsIgnoreCase("Alcaldes"))
                .collect(Collectors.toList());
        if (candidatosAlcalde.isEmpty()){
            System.out.println("No hay candidatos diponibles para esta elección");
            return;
        } 
        int i = 1;
        for (Candidatos candidato : candidatosAlcalde){
            System.out.println(i + ". " + candidato.getNombreCandidato());
            i++;
        }
            
        System.out.println("Selecciona el número del candidato por el que deseas votar: ");
        System.out.println("Presiona 0 para volver al menu anterior");
        int opcion = scan.nextInt();
        
        Votante votante = loginVotante(archivoVotantes, archivoVotantes, i);
            
        if (opcion == 0){
            menuVotantes();
        } else if (opcion > 0 && opcion <= candidatosAlcalde.size()){
            Candidatos candidatoSeleccionado = candidatosAlcalde.get(opcion - 1);                
            Voto voto = new Voto (votante, candidatoSeleccionado);
            listaVotos.add(voto);
                
            System.out.println("Has votado por: " + candidatoSeleccionado.getNombreCandidato());
                
        } else {
            System.out.println("Opcion incorrecta. ");
            eleccionPresidente(); 
            }
        
    }

    public static void guardarVotosEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter((archivoVotos)))) {
            for (Voto voto : listaVotos) {
                bw.write(voto.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void menuReportes(){
        Scanner scan = new Scanner (System.in);
        System.out.println("Menu de reportes");
        System.out.println("1. Conteo general de votos");
        System.out.println("2. Votos emitidos por sexo");
        System.out.println("3. Votos por ubicación geográfica");
        System.out.println("4. Reporte maestro");
        
        int opcion = scan.nextInt();
        
        switch (opcion){
            case 1: 
                generarConteoGeneral();
                break;
            case 2:
                generarReportePorSexo();
                break;
            case 3:
                generarReportePorUbicacion();
                break;
            default: 
                System.out.println("Opcion incorrecta");
        }
    }
    
    public static void generarConteoGeneral(){
        Map<Candidatos, Integer> conteoVotosPorCandidato = new HashMap<>();
        int totalVotosEmitidos = 0;
        
        for (Voto voto : listaVotos){
            Candidatos candidatos = voto.getCandidato();
            
            if (conteoVotosPorCandidato.containsKey(candidatos)){
                int votosAnteriores = conteoVotosPorCandidato.get(candidatos);
                conteoVotosPorCandidato.put(candidatos, votosAnteriores + 1);
            } else {
                conteoVotosPorCandidato.put(candidatos, 1);
            }
            
            totalVotosEmitidos++;
        }
        System.out.println("Conteo General de Votos: ");
        for (Map.Entry<Candidatos,Integer> entry: conteoVotosPorCandidato.entrySet()){
            Candidatos candidatos = entry.getKey();
            int votosObtenidos = entry.getValue();
            double porcentajeVotos = (votosObtenidos * 100.0)/totalVotosEmitidos;
            
            System.out.println("Candidato: " + candidatos.getNombreCandidato());
            System.out.println("Votos obtenidos: " + votosObtenidos);
            System.out.println("Porcentaje de votos: " + porcentajeVotos + "%");
            System.out.println("------------------------------------------------------------");
        }
    
        System.out.println("Total de votos emitidos: " + totalVotosEmitidos);    
        
    }
    
    public static void generarReportePorSexo(){
        Scanner scan = new Scanner (System.in);
        int votosMasculinos = 0;
        int votosFemeninos = 0;
        
        for (Voto voto : listaVotos){
            Votante votante = voto.getVotante();
            
            if (votante.getSexo().equalsIgnoreCase("Masculino")){
                votosMasculinos++;
            } else if(votante.getSexo().equalsIgnoreCase("Femenino")){
                votosFemeninos++;
            }
        }
        
        int totalVotos = votosMasculinos + votosFemeninos;
        
        double porcentajeMasculino = (votosMasculinos * 100.0)/ totalVotos;
        double porcentajeFemenino = (votosFemeninos * 100.0)/ totalVotos;
        
        System.out.println("Reporte de votos por Sexo");
        System.out.println("");
        
        System.out.println("Votos Masculinos: "+ votosMasculinos);
        System.out.println("Porcentaje de votos Masculinos: " + porcentajeMasculino + "%");
        System.out.println("Votos Femeninos: " + votosFemeninos);
        System.out.println("Porcentaje de votos Femeninos: " + porcentajeFemenino + "%");
        System.out.println("Total de votos: " + totalVotos);
    }
    
    public static void generarReportePorUbicacion(){
        Map<String, Integer> votosPorPais = new HashMap<>();
        Map<String, Integer> votosPorDepartamento = new HashMap<>();
        Map<String, Integer> votosPorMunicipio = new HashMap<>();
        
        for (Voto voto : listaVotos){
            Votante votante = voto.getVotante();
            
            //cuenta votos por pais
            votosPorPais.put(votante.getPais(),votosPorPais.getOrDefault(votante.getPais(),0)+ 1 );
            
            //cuenta por departamento
            votosPorDepartamento.put(votante.getDepartamento(),votosPorDepartamento.getOrDefault(votante.getDepartamento(), 0)+1);
        
            //cuenta por municipio 
            votosPorMunicipio.put(votante.getMunicipio(),votosPorMunicipio.getOrDefault(votante.getMunicipio(), 0)+1);
            
            System.out.println("Reporte de Votos por Ubicación Geográfica");
            System.out.println("");
            System.out.println("Votos por país: ");
            for (Map.Entry<String, Integer> entry : votosPorPais.entrySet()){
                System.out.println(entry.getKey()+": " + entry.getValue()+ " Votos");
            }
            
            System.out.println("Votos por Departamento:");
            for (Map.Entry<String, Integer> entry : votosPorDepartamento.entrySet()) {
                 System.out.println(entry.getKey() + ": " + entry.getValue() + " votos");
            }

            System.out.println("Votos por Municipio:");
            for (Map.Entry<String, Integer> entry : votosPorMunicipio.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votos");
            }
        }
    }
}
class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private String rol;
    private String estado;
    
    public Usuario(){       
    }

    public Usuario(String nombre, String apellido, String correo, String contraseña, String rol, String estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.estado = estado;
    }

    public String toCSV() {
        return nombre + "," + apellido + "," + correo + "," + contraseña + "," + rol + "," + estado;
    }

    public String ToString(){
        return "Nombre: " + nombre + ", Apellido: " + apellido + ", Correo: " + correo + ", Contraseña: " + contraseña + ", Rol: " + rol +", Estado: " + estado;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
}
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getContraseña (){
        return contraseña;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getRol (){
        return rol;
    }
    
    public void setEstado (String estado) {
        this.estado = estado;
    }

    public String getEstado () {
        return estado;
    }        

    public String getCorreo() {
        return correo;
    }
    
    public  Usuario login(String correoUsuario, String contrasenia){//necesitamos retornar un usuario si existe
        return InicioSesion.listaUsuarios.stream()
                .filter(usuario-> usuario.getCorreo().equalsIgnoreCase(correoUsuario) 
                && 
                usuario.getContraseña().equalsIgnoreCase(contrasenia))
                .findFirst().orElse(null);
    }
}

class Eleccion {
    private String titulo;
    private String proposito;
    private String descripcion;
    private int codigoUnico;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    public Eleccion(){   
    }
    
    public Eleccion(String titulo, String proposito, String descripcion, int codigoUnico, LocalDate fechaInicio, LocalDate fechaFin){
        this.titulo = titulo;
        this.proposito = proposito; 
        this.descripcion = descripcion;
        this.codigoUnico = codigoUnico;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String toCSV(){
        return titulo + "," + proposito + "," + descripcion + "," + codigoUnico + "," + fechaInicio + "," + fechaFin;
    }
    
    public String toString (){
        return "Titulo: "+ titulo +", Proposito: " + proposito + ", Descripcion: " + descripcion + ", Codigo Unico: " + codigoUnico +", Fecha Inicio: " + fechaInicio + "Fecha Fin: "+ fechaFin;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public String getProposito() {
        return proposito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCodigoUnico() {
        return codigoUnico;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigoUnico(int codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}

class Candidatos {
    private String nombreCandidato;
    private String formacion;
    private String experiencia;
    private int codigo;
    private String puesto;
    
    public Candidatos (String nombreCandidato, String formacion, String experiencia, int codigo, String puesto){
        this.nombreCandidato = nombreCandidato;
        this.formacion = formacion;
        this.experiencia = experiencia;
        this.codigo = codigo;
        this.puesto = puesto;
    }
    
    public String toCsv (){
        return nombreCandidato + "," + formacion + "," + experiencia + "," + codigo + ","+ puesto;
    }
    
    public String toString (){
        return "Nombre de Candidato: " + nombreCandidato + ", Formación: " + formacion + ", Experiencia: " + experiencia + ", Codigo: " + codigo + ", Lista de Candidatos: "+ puesto;
    }

    public String getNombreCandidato() {
        return nombreCandidato;
    }

    public String getFormacion() {
        return formacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setNombreCandidato(String nombreCandidato) {
        this.nombreCandidato = nombreCandidato;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }   
}

class Votante {
    private String correoVotante;
    private String nombres;
    private String apellidos;
    private long cui;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String departamento;
    private String municipio;
    private String pais;
    private String contraseniaVotante;
    private String estado;
    
    public Votante (String correoVotante, String nombres, String apellidos, long cui, String sexo, LocalDate fechaNacimiento, String direccion, String departamento, String municipio, String pais, String contraseniaVotante, String estado){
       this.correoVotante = correoVotante;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cui = cui;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.pais = pais;
        this.contraseniaVotante = contraseniaVotante;
        this.estado = estado;
    }
    
    public String toCsv (){
        return correoVotante + "," + nombres + "," + apellidos + "," + cui + "," + sexo + "," + fechaNacimiento + ","+ direccion + "," + departamento + "," + municipio + "," + pais + "," + contraseniaVotante + "," + estado;
    }
    
    public String toString (){
        return "Correo: " + correoVotante + ", Nombres: " + nombres + ", Apellidos: " + apellidos + ", CUI: " + cui + ", Sexo: " + sexo + ", Fecha de Nacimiento: " + fechaNacimiento + ", Dirección: " + direccion + ", Departamento: " + departamento + ", Municipio: " + municipio + ", País: " + pais + ", Contraseña: " + contraseniaVotante + ", Estado: " + estado;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public long getCui() {
        return cui;
    }

    public String getSexo() {
        return sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getPais() {
        return pais;
    }

    public String getContraseniaVotante() {
        return contraseniaVotante;
    }

    public String getEstado() {
        return estado;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCui(long cui) {
        this.cui = cui;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setContraseniaVotante(String contraseniaVotante) {
        this.contraseniaVotante = contraseniaVotante;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreoVotante() {
        return correoVotante;
    }

    public void setCorreoVotante(String correoVotante) {
        this.correoVotante = correoVotante;
    }
}

class Voto {
    private Votante votante;
    private Candidatos candidato;
    
    public Voto (Votante votante, Candidatos candidato){
        this.votante = votante;
        this.candidato = candidato;
    }

    public Votante getVotante() {
        return votante;
    }

    public Candidatos getCandidato() {
        return candidato;
    }

    public void setVotante(Votante votante) {
        this.votante = votante;
    }

    public void setCandidato(Candidatos candidato) {
        this.candidato = candidato;
    }
}
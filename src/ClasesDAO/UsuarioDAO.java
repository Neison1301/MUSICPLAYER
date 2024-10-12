package ClasesDAO;

public class UsuarioDAO {

    String[][] USUARIOS = {
        {"neison", "neison"},
        {"zzz", "zzz"},
        {"aaa", "aaa"}
    };

    public boolean validarUsuario(String usuario, String contraseña) {
        for (String[] usuarioValido : USUARIOS) {
            if (usuarioValido[0].equals(usuario) && usuarioValido[1].equals(contraseña)) {
                return true;
            }
        }
        return false; 
    }
}

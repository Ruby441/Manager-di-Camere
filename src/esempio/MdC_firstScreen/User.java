package esempio.MdC_firstScreen;

public class User {
    private String username;
    private String password;
    private String privilegio;

    public User(String username, String password, String privilegio) {
        this.username = username;
        this.password = password;
        this.privilegio = privilegio;
    }

    // Metodi getter per ottenere i valori degli attributi
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPrivilegio() {
        return privilegio;
    }
}

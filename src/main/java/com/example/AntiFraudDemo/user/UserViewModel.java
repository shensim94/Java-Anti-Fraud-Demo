package antifraud.user;

public class UserViewModel {
    private Long id;

    private String name;

    private String username;

    public UserViewModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}

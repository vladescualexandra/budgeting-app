package ro.ase.csie.degree.firebase;


public class FirebaseObject {

    protected String id;
    protected String user;


    public FirebaseObject(String id, String user) {
        this.id = id;
        this.user = user;
    }

    public FirebaseObject() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


}

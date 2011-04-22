package no.steria.kata.javaee;

import java.io.PrintWriter;

public class CreatePersonView {

    private String fullNameError;
    private String fullName = "";

    public void setFullName(String fullName) {
        this.fullName = fullName;
        fullNameError = validateName(fullName);
    }
    
    void showCreatePage(PrintWriter writer) {
        writer.append("<html>");
        writer.append("<head><style>#error { color: red; }</style></head>");
    
        if (fullNameError != null) {
            writer.append("<div id='error'>").append(fullNameError).append("</div>");
        }
        writer //
            .append("<form method='post' action='createPerson.html'>") //
            .append("<p>")
            .append("<label for='full_name'><b>Full name:</b></label>")
            .append("<input type='text' name='full_name' value='" + htmlEscape(fullName) + "'/>") //
            .append("</p>")
            .append("<input type='submit' name='createPerson' value='Create person'/>") //
            .append("</form>");
        writer.append("</html>");
    }

    private String htmlEscape(String fullName) {
        return fullName.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    static boolean containsIllegalCharacters(String fullName) {
        String illegals = "<>&";
        for (char illegal : illegals.toCharArray()) {
            if (fullName.contains(Character.toString(illegal))) return true;
        }
        return false;
    }

    static String validateName(String fullName) {
        String errorMessage = null;
        if (fullName.equals("")) {
            errorMessage = "Full name must be given";
        } else if (CreatePersonView.containsIllegalCharacters(fullName)) {
            errorMessage = "Full name contains illegal characters";
        }
        return errorMessage;
    }

    public boolean isValid() {
        return fullNameError == null;
    }

}

package no.steria.kata.javaee;

import java.io.PrintWriter;

public class CreatePersonForm {

    private String firstName = "";
    private String firstNameError;
    private String lastName = "";
    private String lastNameError;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        firstNameError = validateName(firstName, "First name");
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
        lastNameError = validateName(lastName, "Last name");
    }
    
    void showCreateForm(PrintWriter writer) {
        writer.append("<html>");
        writer.append("<head><style>#error { color: red; }</style></head>");
    
        if (firstNameError != null) {
            writer.append("<div id='error'>").append(firstNameError).append("</div>");
        }
        if (lastNameError != null) {
            writer.append("<div id='error'>").append(lastNameError).append("</div>");
        }
        writer //
            .append("<form method='post' action='createPerson.html'>") //
            .append("<p>")
            .append("<label for='full_name'><b>Full name:</b></label>")
            .append("<input type='text' name='first_name' value='" + htmlEscape(firstName) + "'/>") //
            .append("<input type='text' name='last_name' value='" + htmlEscape(lastName) + "'/>") //
            .append("</p>")
            .append("<input type='submit' name='createPerson' value='Create person'/>") //
            .append("</form>");
        writer.append("</html>");
    }

    private String htmlEscape(String fullName) {
        if (fullName == null) return "";
        return fullName.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    private boolean containsIllegalCharacters(String fullName) {
        if (fullName == null) return false;
        String illegals = "<>&";
        for (char illegal : illegals.toCharArray()) {
            if (fullName.contains(Character.toString(illegal))) return true;
        }
        return false;
    }

    private String validateName(String firstName, String nameString) {
        String errorMessage = null;
        if ("".equals(firstName)) {
            errorMessage = nameString +  " must be given";
        } else if (containsIllegalCharacters(firstName)) {
            errorMessage = nameString + " contains illegal characters";
        }
        return errorMessage;
    }

    public boolean isValid() {
        return firstNameError == null && lastNameError == null;
    }


}

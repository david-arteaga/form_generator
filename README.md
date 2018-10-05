# Form generator

This project is meant to be used to generate HTML code for forms, from java classes. It takes into account the following types of fields:
- Boolean
- Date
- List (of primitive or other custom entity)
- Number
- String
- Other custom entity

I built this project to enable generating HTML code from a Java class and then receive the form data in an instance of the same class used to generate the form.

At the moment there is support for the following formats:
- Thymeleaf templates (using Bootstrap http://getbootstrap.com/)
- Angular (using components from https://material.angular.io/)

## Usage
Annotate the classes you would like to generate forms for using `@FormEntity`. This will generate a form using the fields inside of the annotated class. This will also enable this class to be used as a custom entity in another form.
```
@FormEntity(generateForm = true)
public class UserForm {
  String firstname;
  String lastname;
  Integer age;
  LocalDate birthdate;
  List<String> tags;
}
```


## Other annotations
#### `@FormIgnore`
Use this annotation to ignore fields in the class and have then not render to HTML.


#### `@FormHidden`
Use this annotation to hide fields in the form, but have them present in the HTML anyway. Usefull for `id` fields.


#### `@ListTypeReferencesFormEntity`
This is intended to be used on elements that are of a `java.util.List` type, in which the type argument to the `List` is a primitive. The main use case would be for a field in a class used to represent the list of values directly returned from an HTML form, for a given input.
For example, have a field
```
@FormEntity
public class Post {

  @ListTypeReferencesFormEntity(Post.class)
  List<Integer> relatedPosts;
  
  // ...
}
```
This way, you can use the form as it is to receive the data from an HTML request and generate the adequate HTML code.


#### `@ReferencesFormEntity`
Use this annotation of fields of a primitive type, that represent another Entity. For example, a field `Integer user;` that represents the user id that is returned from the HTMl form.
```
@FormEntity
class SomeFormClass {
  @ReferencesFormEntity(User.class)
  Innteger user; // will receive the  user id
}
```


#### `@PredefinedType`
Use this annotation for fields that are of a type whose definition you don't have access to. These types can't be annotated with `@FormEntity`, so use this annotation to indicate the  name for the `id` and `name` fields for the type.
```
@FormEntity
class SomeFormClass {
  @PredefinedType(idFieldName = "customIdField", nameFieldName = "customNameField")
  OtherType value;
}
```

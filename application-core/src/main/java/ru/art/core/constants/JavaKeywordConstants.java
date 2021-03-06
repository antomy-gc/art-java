package ru.art.core.constants;

import lombok.*;
import static java.util.EnumSet.*;

@Getter
@AllArgsConstructor
public enum JavaKeywordConstants {
  ABSTRACT("abstract"), CONTINUE("continue"), FOR("for"), NEW("new"), SWITCH("switch"), ASSERT("assert"),
  DEFAULT("default"), GOTO("goto"), PACKAGE("package"), SYNCHRONIZED("synchronized"), BOOLEAN("boolean"),
  DO("do"), IF("if"), PRIVATE("private"), THIS("this"), BREAK("break"), DOUBLE("double"),
  IMPLEMENTS("implements"), PROTECTED("protected"), THROW("throw"), BYTE("byte"), ELSE("else"),
  IMPORT("import"), PUBLIC("public"), THROWS("throws"), CASE("case"), ENUM("enum"), INSTANCEOF("instanceof"),
  RETURN("return"), TRANSIENT("transient"), CATCH("catch"), EXTENDS("extends"), INT("int"), SHORT("short"),
  TRY("try"), CHAR("char"), FINAL("final"), INTERFACE("interface"), STATIC("static"), VOID("void"),
  CLASS("class"), FINALLY("finally"), LONG("long"), STRICTFP("strictfp"), VOLATILE("volatile"),
  CONST("const"), FLOAT("float"), NATIVE("native"), SUPER("super"), WHILE("while");

  private final String word;

  public static boolean contains(String value) {
    return allOf(JavaKeywordConstants.class).stream().anyMatch(keyword -> keyword.name().equalsIgnoreCase(value));
  }
}

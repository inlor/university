export class FieldModel {
  constructor(public line: number, public indent: number, public content: string) {
  }

  public static equalsArray(array1: FieldModel[], array2: FieldModel[]): boolean {
    if (array1.length !== array2.length) {
      return false;
    }
    for (let i = 0; i < array1.length; i++) {
      if (!array2[i].equals(array1[i])) {
        return false;
      }
    }
    return true;
  }

  public equals(field: FieldModel): boolean {
    return this.indent === field.indent && this.content === field.content && this.line === field.line;
  }

  public errorIndent(field: FieldModel): boolean {
    return this.indent !== field.indent && this.content === field.content;
  }

  public errorLine(field: FieldModel): boolean {
    return this.indent === field.indent && this.content !== field.content;
  }
}

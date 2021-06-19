export class Model {
  id: number;
  createdAt: string;
  updatedAt: string;

  deserialize(input: any): this {
    Object.assign(this, input);
    return this;
  }

  serialize(): string {
    return JSON.stringify(this);
  }
}

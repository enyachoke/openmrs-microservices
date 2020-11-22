export interface IConceptProposal {
  id?: number;
  uuid?: string;
  encounter?: string;
  originalText?: string;
  finalText?: string;
  obsUuid?: string;
  obsConceptUuid?: string;
  state?: string;
  comments?: string;
  locale?: string;
}

export class ConceptProposal implements IConceptProposal {
  constructor(
    public id?: number,
    public uuid?: string,
    public encounter?: string,
    public originalText?: string,
    public finalText?: string,
    public obsUuid?: string,
    public obsConceptUuid?: string,
    public state?: string,
    public comments?: string,
    public locale?: string
  ) {}
}

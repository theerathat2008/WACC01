package IdentifierObjects;

public class PairObj extends IDENTIFIER {

  IDENTIFIER LHS;
  IDENTIFIER RHS;

  public PairObj(String name, IDENTIFIER LHS, IDENTIFIER RHS) {
    this.name = name;
    this.LHS = LHS;
    this.RHS = RHS;
  }

  /**
   * Compares value for equality
   * @param other
   */
  public boolean equals(IDENTIFIER other) {
    if (other instanceof PairObj) {
      if (((PairObj) other).LHS.equals(LHS) && ((PairObj) other).RHS.equals(RHS)) {
        return true;
      }
    } else if (other instanceof BaseTypeObj) {
      if (other.toString().equals("pair")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get String object representation of this class
   */
  public String toString() {
    return "PAIR(" + LHS.toString() + "," + RHS.toString() + ")";
  }
}

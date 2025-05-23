package com.vieecoles.Conduite;

public class NoteConducte {
  private float pointsAssiduite;
  private float pointsTenue;
  private float pointsMoralite;
  private float pointsDiscipline;
  private float noteTotal;
  private String appreciation;
  private boolean eligibleDistinction;

  public NoteConducte(float pointsAssiduite, float pointsTenue, float pointsMoralite,
                      float pointsDiscipline, float noteTotal, String appreciation,
                      boolean eligibleDistinction) {
    this.pointsAssiduite = pointsAssiduite;
    this.pointsTenue = pointsTenue;
    this.pointsMoralite = pointsMoralite;
    this.pointsDiscipline = pointsDiscipline;
    this.noteTotal = noteTotal;
    this.appreciation = appreciation;
    this.eligibleDistinction = eligibleDistinction;
  }

  // Getters
  public float getPointsAssiduite() {
    return pointsAssiduite;
  }

  public float getPointsTenue() {
    return pointsTenue;
  }

  public float getPointsMoralite() {
    return pointsMoralite;
  }

  public float getPointsDiscipline() {
    return pointsDiscipline;
  }

  public float getNoteTotal() {
    return noteTotal;
  }

  public String getAppreciation() {
    return appreciation;
  }

  public boolean isEligibleDistinction() {
    return eligibleDistinction;
  }
}

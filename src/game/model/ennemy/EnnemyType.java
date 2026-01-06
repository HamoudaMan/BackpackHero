package game.model.ennemy;

import game.model.representation.Action;

public enum EnnemyType {
  SMALL_RATWOLF(new Action[] {Action.ATTACK, Action.ATTACK, Action.BLOCK}), 
  RATWOLF(new Action[] {Action.ATTACK, Action.BLOCK, Action.ATTACK, Action.BLOCK}),
  SLIME(new Action[] {Action.ATTACK, Action.HEAL, Action.ATTACK, Action.HEAL});
  
  private final Action[] pattern;

  private EnnemyType(Action[] pattern) {
    this.pattern = pattern;
  }
  
  public Action[] getPattern() {
    return pattern;
  }
}
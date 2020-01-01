package Pokemons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

// This class is only for reference a damage multiplier in the DamageManager class
public class AttackAndTargetTypes {
    public final PokemonType attackType;
    public final HashSet<PokemonType> targetTypes;

    public AttackAndTargetTypes(PokemonType attackType, HashSet<PokemonType> targetTypes) {
        this.attackType = attackType;
        this.targetTypes = targetTypes;
    }
    public PokemonType getAttackType() {
        return attackType;
    }
    public HashSet<PokemonType> getTargetTypes() {
        return targetTypes;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttackAndTargetTypes that = (AttackAndTargetTypes) o;
        return Objects.equals(attackType, that.attackType) &&
                Objects.equals(targetTypes, that.targetTypes);
    }
    @Override
    public int hashCode() {
        return Objects.hash(attackType, targetTypes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttackAndTargetTypes{");
        sb.append("attackType=").append(attackType);
        sb.append(", targetTypes=").append(targetTypes);
        sb.append('}');
        return sb.toString();
    }
}

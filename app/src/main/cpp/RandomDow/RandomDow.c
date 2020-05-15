//
// Created by Probak on 15-May-20.
//

#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "RandomDow.h"

#define MAX_FACTION_SIZE 32
#define NB_FACTIONS 9

int random_int(int min, int max) {
    return min + rand() / (RAND_MAX / (max - min + 1) + 1);
}

const char * get_random_faction() {
    srand((unsigned int) time(0));
    const char factions[NB_FACTIONS][MAX_FACTION_SIZE] = {
            "Eldars", "Tau", "Imperial Guard", "Orks", "Space Marines", "Necrons",
            "Sisters of battle", "Dark eldars", "Chaos"
    };
    int rand_int = random_int(0, NB_FACTIONS - 1);
    char * faction = (char *) malloc(MAX_FACTION_SIZE * sizeof(char));
    strcpy(faction, factions[rand_int]);
    return faction;
}

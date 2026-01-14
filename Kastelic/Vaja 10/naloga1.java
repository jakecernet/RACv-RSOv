/* Oseba je opredeljena z imenom, priimkom in letnim dohodkom. Sestavite razred (ali record) z imenom 'Os', ki bo ustrezal opredelitvam. 
Zagotovite, da bodo lastnosti privatne, ter da razreda ne bo moč dedovati. */

final record Os(String ime, String priimek, double letniDohodek) {

}
/* Izrišite razredni diagram za razred Segment., MOverFlowException in MUnderFlowException. 
 *
 *  +---------------------------------+      +-----------------------------+
 *  |           <<class>>             |      |        <<exception>>        |
 *  |           Segment               |      |     MOverFlowException      |
 *  +---------------------------------+      +-----------------------------+
 *  | - stanje: int                   |      | (extends Exception)         |
 *  | - nasl: Segment (0..1)          |      | + MOverFlowException()      |
 *  | - pred: Segment (0..1)          |      | + MOverFlowException(msg)   |
 *  +---------------------------------+      +-----------------------------+
 *  | + Segment()                     |
 *  | + Segment(pred: Segment)        |
 *  | + getStanje(): int              |
 *  | + overflow(): void              |
 *  | + up(): void throws MOverFlowException |
 *  | + down(): void                  |
 *  | + setNasl(n: Segment): void     |
 *  | + setPrev(p: Segment): void     |
 *  | + toString(): String            |
 *  +---------------------------------+
 *            ^    ^
 *            |    |
 *       nasl |    | pred
 *   (association, 0..1) 
 *
 *  +-----------------------------+
 *  |        <<exception>>        |
 *  |    MUnderFlowException      |
 *  +-----------------------------+
 *  | (extends Exception)         |
 *  | + MUnderFlowException()     |
 *  | + MUnderFlowException(msg)  |
 *  +-----------------------------+
 *
 * Opombe:
 * - Relacija Segment -> Segment (nasl, pred) je obična asociacija (vsak segment lahko kaže na naslednjega in prejšnjega).
 * - Metoda up() deklarira "throws MOverFlowException" — metoda lahko sproži to izjemo.
 * - V trenutni implementaciji se v overflow() poskuša klicati nasl.up() in se ujame MOverFlowException, nato rekurzivno kliče nasl.overflow().
 */

/*
Dodajte implementacijo štetja navzdol in izvedite demo program, kot je
  izveden s štetjem navzgor.
  
  for (int i=0;i<330;i++)
           try {
               s.down();
           } catch(MUnderFlowException e){
               s.underflow();
           } finally {
               showAllSegments( s );
           }

    public void down() throws MUnderFlowException{
        stanje--;
        if (stanje<0)
            throw new MUnderFlowException();
    }

    public void underflow(){
        // when underflow occurs, set this segment to 9 and try to decrement the next segment
        stanje = 9;
        try {
            nasl.down();
        } catch ( NullPointerException e){
            // reached end, nothing more to decrement
        } catch ( MUnderFlowException e ){
            System.out.println(e);
            nasl.underflow();
        }
    }
 */
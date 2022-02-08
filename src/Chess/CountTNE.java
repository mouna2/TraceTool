package Chess;

public class CountTNE {
		public int CountT=0; 
		public int CountN=0; 
		public int CountE=0;
		
		public int getCountT() {
			return CountT;
		}
		public void setCountT(int countT) {
			CountT = countT;
		}
		public int getCountN() {
			return CountN;
		}
		public void setCountN(int countN) {
			CountN = countN;
		}
		public int getCountE() {
			return CountE;
		}
		public void setCountE(int countE) {
			CountE = countE;
		}
		public CountTNE() {
			super();
		}
		@Override
		public String toString() {
			return "CountTNE [CountT=" + CountT + ", CountN=" + CountN + ", CountE=" + CountE + "]";
		} 
		
		
		
		
}

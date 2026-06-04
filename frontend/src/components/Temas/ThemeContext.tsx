import { createContext, useContext, useState } from "react";

const ThemeContext = createContext({ modoOscuro: false, toggleModo: () => {} });

export function ThemeProvider({ children }: { children: React.ReactNode }) {
  const [modoOscuro, setModoOscuro] = useState(false);
  const toggleModo = () => setModoOscuro(!modoOscuro);

  return (
    <ThemeContext.Provider value={{ modoOscuro, toggleModo }}>
      {children}
    </ThemeContext.Provider>
  );
}

export const useTheme = () => useContext(ThemeContext);
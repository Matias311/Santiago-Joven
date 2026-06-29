import { useState } from "react";
import { ThemeContext } from "./ThemeContext";

export function ThemeProvider({ children }: { children: React.ReactNode }) {
  const [modoOscuro, setModoOscuro] = useState(false);

  const toggleModo = () => setModoOscuro(!modoOscuro);

  return (
    <ThemeContext.Provider value={{ modoOscuro, toggleModo }}>
      {children}
    </ThemeContext.Provider>
  );
}

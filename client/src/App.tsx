import { QueryClient, QueryClientProvider } from "react-query";

import "antd/dist/antd.css";
import { Layout } from "./components/Layout/Layout";
import { useEffect, useState } from "react";
import { Login } from "./components/Login/Login";

const queryClient = new QueryClient();

function App() {
  const [logged, setLogged] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setLogged(true);
    }
  }, []);

  const onLogin = (token: string) => {
    localStorage.setItem("token", token);
    setLogged(true);
  };

  return (
    <QueryClientProvider client={queryClient}>
      {logged ? <Layout /> : <Login onLogin={onLogin} />}
    </QueryClientProvider>
  );
}

export default App;

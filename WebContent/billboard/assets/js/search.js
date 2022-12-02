const useState = React.useState;
const useEffect = React.useEffect;

const Search = () => {
  const [value, setValue] = useState("");
  const handleValueChange = (event) => {
    setValue(event.target.value);
  };
  return (
    <>
      <Header />
      <h1>アルバムを検索</h1>
      <p>(1-100の数字を入力するとアルバム情報を取得できます)</p>
      <form action="/result.html" method="get">
        <input value={value} name="value" onChange={handleValueChange} />
        <input type="submit" value="検索！" />
      </form>
    </>
  );
};

const Header = () => {
  return (
    <header>
      <a href="/index.html" className="logo">
        Billboard Kindai
      </a>
      <nav>
        <ul className="nav-list">
          <li className="nav-list-item">
            <a href="/index.html">ホーム</a>
          </li>
          <li clasName="nav-list-item">
            <a href="/search.html">検索</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<Search />);

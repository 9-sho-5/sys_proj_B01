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
      <div className="page-container">
        <div className="flex-container">
          <div className="title-container">
            <h1>FIND TRACKS</h1>

            <form action="./result.html" method="get" className="search-form">
              <input
                value={value}
                name="value"
                onChange={handleValueChange}
                className="search-input"
                placeholder="検索ワード"
              />
              <input
                type="submit"
                value="検索"
                className="search-input-button"
              />
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

const Header = () => {
  return (
    <header>
      <a href="./index.html" className="logo">
        Billboard Kindai
      </a>
      <nav>
        <ul className="nav-list">
          <li className="nav-list-item">
            <a href="./index.html">ホーム</a>
          </li>
          <li clasName="nav-list-item">
            <a href="./search.html">検索</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<Search />);

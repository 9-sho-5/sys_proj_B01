const useState = React.useState;
const useEffect = React.useEffect;

const Ranking = () => {
  const [albums, setAlbums] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const url = new URL(window.location.href);
  const params = url.searchParams;

  useEffect(() => {
    setIsLoading(true);
    fetch(`https://jsonplaceholder.typicode.com/albums`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("error");
        }
        console.log(response);
        return response.json();
      })
      .then((data) => {
        setAlbums(data);
        setIsLoading(false);
        console.log(data);
      })
      .catch((error) => {
        setError("取得できませんでした");
        setIsLoading(false);
      });
  }, []);

  return (
    <>
      <Header />
      <h1>楽曲ランキング</h1>
      {albums && albums.map((album) => <Album album={album} />)}
      <div>
        {isLoading && <p>Loading...</p>}
        {error && <p>{error}</p>}
        <a href="/index.html">戻る</a>
      </div>
    </>
  );
};

const Album = ({ album }) => {
  return (
    <div className="album">
      <img src="./img/entertainment_music.png" alt="album art" />
      <p> {album.id}</p>
      <p>アルバム名: {album.title}</p>
      <p>曲名: hogehoge</p>
      <button>リクエストする</button>
    </div>
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
root.render(<Ranking />);

import { getSearch } from "./assets/js/get-search.js";
const useState = React.useState;
const useEffect = React.useEffect;

const Result = () => {
  const [albums, setAlbums] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [requestResult, setRequestResult] = useState("");
  const url = new URL(window.location.href);
  const params = url.searchParams;
  const value = params.get("value");

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      if (!value) {
        setError("キーワードを入力してください");
        setIsLoading(false);
      } else {
        const searchResult = await getSearch(value);
        searchResult
          ? setAlbums(searchResult.data)
          : setError("楽曲が見つかりませんでした");
        setIsLoading(false);
      }
    })();
  }, []);

  return (
    <>
      <Header />
      <h1>検索結果</h1>
      {albums &&
        albums.map((album) => (
          <Album album={album} handleRequest={handleRequest} />
        ))}
      <div>
        {isLoading && <p>Loading...</p>}
        {error && <p>{error}</p>}
        <a href="./search.html">検索画面へ戻る</a>
      </div>
    </>
  );
};

const Album = ({ album, handleRequest }) => {
  return (
    <div className="album">
      <img src={album.album_image_url} alt="album art" />
      <p>アルバム名: {album.album_name}</p>
      <p>曲名: {album.track_name}</p>
      <p>アーティスト名: {album.artist_name}</p>
      <button>リクエストする</button>
    </div>
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
root.render(<Result />);
